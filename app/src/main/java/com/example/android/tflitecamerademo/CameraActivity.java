/* Copyright 2017 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/** Main {@code Activity} class for the Camera app. */
public class CameraActivity extends Activity {
  private AutoFitTextureView textureView;
  private ImageClassifier classifier;
  private TextView textView;
  private static final String TAG = "TfLiteCameraDemo";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.camera);
    ImageView imageView=findViewById(R.id.texture);
    textView = (TextView) findViewById(R.id.text);
    Bitmap bitmap=getBitmapFromAsset(this,"mouse.png");
    imageView.setImageBitmap(bitmap);
    try {
      // create either a new ImageClassifierQuantizedMobileNet or an ImageClassifierFloatInception
//      classifier = new ImageClassifierQuantizedMobileNet(this);
      classifier = new ImageClassifierFloatInception(this);
    } catch (IOException e) {
      Log.e(TAG, "Failed to initialize an image classifier.", e);
    }
//    for (int i=0;i<3;i++) {
//      classifyFrame();
//     Log.e(TAG,"test");
//    }
    classifyFrame();


//    if (null == savedInstanceState) {
//      getFragmentManager()
//          .beginTransaction()
//          .replace(R.id.container, Camera2BasicFragment.newInstance())
//          .commit();
//    }
  }
  private void classifyFrame() {
//    if (classifier == null || getActivity() == null || cameraDevice == null)
    if(classifier==null)
    {
      showToast("Uninitialized Classifier or invalid context.");
      return;
    }

//    Bitmap bitmap = textureView.getBitmap(classifier.getImageSizeX(), classifier.getImageSizeY());
    Bitmap bitmap=getBitmapFromAsset(this,"mouse.png");
    bitmap=Bitmap.createScaledBitmap(bitmap,classifier.getImageSizeX(),classifier.getImageSizeY(),false);
    String textToShow = classifier.classifyFrame(bitmap);
//    bitmap.recycle();
    showToast(textToShow);
  }
  private void showToast(final String text) {

                  textView.setText(text);


  }
  public static Bitmap getBitmapFromAsset(Context context, String filePath) {
    AssetManager assetManager = context.getAssets();

    InputStream istr;
    Bitmap bitmap = null;
    try {
      istr = assetManager.open(filePath);
      bitmap = BitmapFactory.decodeStream(istr);
    } catch (IOException e) {
      // handle exception
    }

    return bitmap;
  }
}
