package org.android.turnaround.presentation.roomsetting

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.*
import android.text.Html
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentRoomSettingTakePhotoBinding
import org.android.turnaround.presentation.base.BaseFragment
import java.io.*

class RoomSettingTakePhotoFragment :  BaseFragment<FragmentRoomSettingTakePhotoBinding>(R.layout.fragment_room_setting_take_photo) {
    private var mHandler: Handler? = null
    private lateinit var mPreviewBuilder: CaptureRequest.Builder
    private lateinit var mImageReader: ImageReader
    private lateinit var mCameraDevice: CameraDevice
    private var step = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 화면 켜짐 유지
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        initView()
        setBtnTakePhotoClickListener()
        setLayout(step, null)
    }

    private fun setLayout(step: Int, bitmap: Bitmap?) {
        when(step) {
            0 -> {
                // 1. text 변경
                val tvTitle1 = "방 중앙에 서서 <font color=#a87eff>천장 몰딩</font>을 맞춰주세요."
                binding.tvTitle1.text = Html.fromHtml(tvTitle1, Html.FROM_HTML_MODE_LEGACY)
                binding.tvTitle2.text =   "준비가되면 촬영버튼을 눌러 순서대로 진행해주세요."
                // 2. surface view text
                val tvSurface = "<font color=#671fff>좌측</font>에서 <font color=#671fff>우측</font>으로 촬영을 진행해주세요"
                binding.tvSurfaceView.text =  Html.fromHtml(tvSurface, Html.FROM_HTML_MODE_LEGACY)
            }
            1 -> {
                // 1. text 변경
                val text1 = "<font color=#a87eff>오른쪽으로 약간 돌아주세요.</font>"
                val text2 = "다시 촬영버튼을 누릅니다."
                binding.tvTitle1.text =  Html.fromHtml(text1, Html.FROM_HTML_MODE_LEGACY)
                binding.tvTitle2.text =  text2
                // 2. grid 화살표 추가
                // 3. 카메라 view 이동
                binding.ivRoom2.setBackgroundResource(R.drawable.bg_rectangle_a87eff)
                binding.tvCountPhotoImage.text = "촬영2"
                moveCameraView(binding.ivRoom2.id)
                // 4. 촬영한 bitmap 넣기
                binding.ivRoom1.setImageBitmap(bitmap)
                // 5. text count 변경
                binding.tvCountPhoto.text = "1/4"
                // 6. 버튼 count 변경
                binding.btnTakePhoto.text = "촬영하기(1/4)"
            }
            2 -> {
                // 1. text 변경 없음
                // 2. grid 화살표 추가
                // 3. 카메라 view 이동
                binding.ivRoom3.setBackgroundResource(R.drawable.bg_rectangle_a87eff)
                binding.tvCountPhotoImage.text = "촬영3"
                moveCameraView(binding.ivRoom3.id)
                // 4. 촬영한 bitmap 넣기
                binding.ivRoom2.setImageBitmap(bitmap)
                binding.ivRoom2Rec.visibility = View.VISIBLE
                // 5. text count 변경
                binding.tvCountPhoto.text = "2/4"
                // 6. 버튼 count 변경
                binding.btnTakePhoto.text = "촬영하기(2/4)"
            }
            3 -> {
                // 1. text 변경
                val text1 = "총 4번의 촬영이 끝났어요!"
                val text2 = "<font color=#a87eff>완료버튼</font>을 눌러주세요!"
                binding.tvTitle1.text = text1
                binding.tvTitle2.text = Html.fromHtml(text2, Html.FROM_HTML_MODE_LEGACY)
                // 2. grid 화살표 그대로
                // 3. 카메라 view 이동
                binding.ivRoom4.setBackgroundResource(R.drawable.bg_rectangle_a87eff)
                binding.tvCountPhotoImage.text = "촬영4"
                moveCameraView(binding.ivRoom4.id)
                // 4. 촬영한 bitmap 넣기
                binding.ivRoom3.setImageBitmap(bitmap)
                binding.ivRoom3Rec.visibility = View.VISIBLE
                // 5. text count 변경
                binding.tvCountPhoto.text = "3/4"
                // 6. 버튼 count 변경
                binding.btnTakePhoto.text = "촬영하기(3/4)"
            }
            4 -> {
                // 1. text 그대로
                // 2. grid invisible
                // 3. 완료 이미지
                // 4. 촬영한 bitmap 넣기
                binding.ivRoom4.setImageBitmap(bitmap)
                binding.ivRoom4Rec.visibility = View.VISIBLE
                // 5. text count 변경
                binding.tvCountPhoto.text = "4/4"
                // 6. 버튼 text 변경
                binding.btnTakePhoto.text = "턴어라운드 완료!"
                // 7. surface image, text 변경
                binding.ivSurfaceView.setImageResource(R.drawable.ic_checking_success)
                binding.tvSurfaceView.text = Html.fromHtml("<font color=#67b321>완료</font>", Html.FROM_HTML_MODE_LEGACY)
            }
        }
    }

    private fun moveCameraView(targetId: Int) {
        val constraints = ConstraintSet()
        constraints.clone(binding.constraintLayout)
        constraints.connect(binding.icCamera.id, ConstraintSet.TOP,
            targetId, ConstraintSet.TOP)
        constraints.connect(binding.icCamera.id, ConstraintSet.START,
            targetId, ConstraintSet.START)
        constraints.connect(binding.icCamera.id, ConstraintSet.END,
            targetId, ConstraintSet.END)
        constraints.connect(binding.tvCountPhotoImage.id, ConstraintSet.BOTTOM,
            targetId, ConstraintSet.BOTTOM)
        constraints.connect(binding.tvCountPhotoImage.id, ConstraintSet.START,
            targetId, ConstraintSet.START)
        constraints.connect(binding.tvCountPhotoImage.id, ConstraintSet.END,
            targetId, ConstraintSet.END)
        constraints.applyTo(binding.constraintLayout)
    }

    private fun initView() {
        binding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                initCameraAndPreview()
            }
            override fun surfaceDestroyed(holder: SurfaceHolder) {
                mCameraDevice.close()
            }
            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }
        })
    }

    private fun setBtnTakePhotoClickListener() {
        binding.btnTakePhoto.setOnClickListener {
            takePicture() // 사진 찍기
        }
    }

    // 카메라와 미리보기 초기화
    private fun initCameraAndPreview() {
        val handlerThread = HandlerThread("CAMERA2")
        handlerThread.start()
        mHandler = Handler(handlerThread.looper)
        openCamera()
    }

    private fun openCamera() {
        try {
            val mCameraManager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val characteristics = mCameraManager.getCameraCharacteristics("0")
            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            val largestPreviewSize = map!!.getOutputSizes(ImageFormat.JPEG)[0]
            mImageReader = ImageReader.newInstance(
                largestPreviewSize.width,
                largestPreviewSize.height,
                ImageFormat.JPEG,
                7
            )
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) return
            mCameraManager.openCamera("0", deviceStateCallback, mHandler)
        } catch (e: CameraAccessException) {
            Log.d("mmm", "카메라를 열지 못했습니다.")
        }
    }

    // 카메라 열 때 콜백: 카메라 미리보기 보여주기
    private val deviceStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            mCameraDevice = camera
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) takePreview()
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
        override fun onDisconnected(camera: CameraDevice) {
            camera.close()
        }
        override fun onError(camera: CameraDevice, error: Int) {
            Log.d("mmm", "카메라를 열지 못했습니다.")
        }
    }

    // 카메라 미리보기 보여주기
    @RequiresApi(Build.VERSION_CODES.P)
    @Throws(CameraAccessException::class)
    private fun takePreview() {
        mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        mPreviewBuilder.addTarget(binding.surfaceView.holder.surface)
        val outputSurface = arrayListOf<Surface>(binding.surfaceView.holder.surface, mImageReader.surface)
        mCameraDevice.createCaptureSession(outputSurface, mSessionPreviewStateCallback, mHandler)
    }

    // 사진찍을 때 호출하는 메서드
    private fun takePicture() {
        try {
            // 이미지 저장하고, 화면에 띄우기
            saveImageFile()
            // mSessionPreviewStateCallback2를 통해 이미지 캡쳐 후 카메라 미리보기 세션 재시작
            val outputSurface = arrayListOf<Surface>(mImageReader.surface)
            mCameraDevice.createCaptureSession(outputSurface, mSessionPreviewStateCallback2, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    // 이미지 저장하고 화면에 띄우는 메소드
    private fun saveImageFile() {
        // 캡쳐한 이미지를 저장할 파일 생성
        val file = File(Environment.getExternalStorageDirectory().toString() + "/pic${step}.jpg")
        val readerListener = ImageReader.OnImageAvailableListener {
            var image : Image? = null
            try {
                image = mImageReader.acquireLatestImage()
                val buffer = image!!.planes[0].buffer
                val bytes = ByteArray(buffer.capacity())
                buffer.get(bytes)
                var output: OutputStream? = null
                try {
                    output = FileOutputStream(file)
                    output.write(bytes)
                } finally {
                    output?.close()
                    // 촬영한 이미지 화면에 띄우기
                    val bitmap: Bitmap = BitmapFactory.decodeFile(file.path)
                    val rotateMatrix = Matrix() // 비트맵 사진이 90도 돌아가있는 문제를 해결하기 위해 90도 rotate
                    rotateMatrix.postRotate(90F)
                    val rotatedBitmap: Bitmap = Bitmap.createBitmap(bitmap, 0,0, bitmap.width, bitmap.height, rotateMatrix, false)
                    step++
                    setLayout(step, rotatedBitmap)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                image?.close()
            }
        }
        // 이미지 저장
        mImageReader.setOnImageAvailableListener(readerListener, null)
    }

    // 미리보기 콜백
    private val mSessionPreviewStateCallback = object : CameraCaptureSession.StateCallback() {
        override fun onConfigured(session: CameraCaptureSession) {
            try {
                // 오토포커싱 계속 동작
                mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                session.setRepeatingRequest(mPreviewBuilder.build(), null, mHandler)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
        override fun onConfigureFailed(session: CameraCaptureSession) {
            Log.d("mmm", "room setting photo 카메라 구성 실패")
        }
    }
    // 이미지 캡쳐 후 카메라 미리보기 재시작
    private val mSessionPreviewStateCallback2 = object : CameraCaptureSession.StateCallback() {
        override fun onConfigured(session: CameraCaptureSession) {
            try {
                // 캡쳐하기
                val captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
                captureBuilder.addTarget(mImageReader.surface)
                captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
                session.capture(captureBuilder.build(), null, null)
                // 미리보기 재시작
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) takePreview()
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
        override fun onConfigureFailed(session: CameraCaptureSession) {
            Log.d("mmm", "room setting photo 카메라 구성 실패")
        }
    }
}