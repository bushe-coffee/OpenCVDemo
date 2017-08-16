package com.opencv.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.opencv.recognition.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class OpenCVActivity extends Activity {

    private Button btn;
    private ImageView img;

    private Bitmap srcBitmap;
    private Bitmap grayBitmap;
    private static boolean flag = true;
    private static boolean isFirst = true;
    private static final String TAG = "gao_chun";


    private CameraBridgeViewBase mOpenCvCameraView;
    private Mat mRgba;
    private Mat mFlipRgba;


    static {
        OpenCVLoader.initDebug();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opencv);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.fd_activity_surface_view);
        mOpenCvCameraView.enableView();//
        mOpenCvCameraView.setCvCameraViewListener(listener2);
        mOpenCvCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);//前置摄像头 CameraBridgeViewBase.CAMERA_ID_BACK为后置摄像头

//        img = (ImageView) findViewById(R.id.img);
//        btn = (Button) findViewById(R.id.btn);
//        btn.setOnClickListener(new ProcessClickListener());
    }


    @Override
    protected void onResume() {
        super.onResume();
        //load OpenCV engine and init OpenCV library
       /* OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, getApplicationContext(), mLoaderCallback);
        Log.i(TAG, "onResume sucess load OpenCV...");*/


//        if (!OpenCVLoader.initDebug()) {
//            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
//        } else {
//            Log.d(TAG, "OpenCV library found inside package. Using it!");
//            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onDestroy() {
        super.onDestroy();
        mOpenCvCameraView.disableView();
    }


    //OpenCV库加载并初始化成功后的回调函数
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case BaseLoaderCallback.SUCCESS:
                    Log.i(TAG, "成功加载");
                    break;
                default:
                    super.onManagerConnected(status);
                    Log.i(TAG, "加载失败");
                    break;
            }
        }
    };


//    public void procSrc2Gray() {
//        Mat rgbMat = new Mat();
//        Mat grayMat = new Mat();
//        srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xxx);
//        grayBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.RGB_565);
//        Utils.bitmapToMat(srcBitmap, rgbMat);//convert original bitmap to Mat, R G B.
//        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);//rgbMat to gray grayMat
//        Utils.matToBitmap(grayMat, grayBitmap); //convert mat to bitmap
//        Log.i(TAG, "procSrc2Gray sucess...");
//    }


//    public class ProcessClickListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//
//            if (isFirst) {
//                procSrc2Gray();
//                isFirst = false;
//            }
//
//            if (flag) {
//                img.setImageBitmap(grayBitmap);
//                btn.setText("查看原图");
//                flag = false;
//            } else {
//                img.setImageBitmap(srcBitmap);
//                btn.setText("灰度化");
//                flag = true;
//            }
//        }
//    }


    //camera 回调数据
    private CameraBridgeViewBase.CvCameraViewListener2 listener2 = new CameraBridgeViewBase.CvCameraViewListener2() {
        @Override
        public void onCameraViewStarted(int width, int height) {
            mRgba = new Mat();
            mFlipRgba = new Mat();
        }

        @Override
        public void onCameraViewStopped() {
            mRgba.release();
        }

        @Override
        public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
            mRgba = inputFrame.rgba();//注意
            Core.flip(mRgba, mFlipRgba, 1);
            return mFlipRgba;
        }
    };


}
