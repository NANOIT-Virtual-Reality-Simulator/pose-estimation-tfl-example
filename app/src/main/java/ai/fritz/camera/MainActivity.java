package ai.fritz.camera;

import android.graphics.Canvas;
import android.media.Image;
import android.util.Size;

import ai.fritz.core.Fritz;
import ai.fritz.visionposeestimationtflmodel.FritzTFLPosePredictor;
import ai.fritz.visionposeestimationtflmodel.FritzVisionImage;
import ai.fritz.visionposeestimationtflmodel.FritzVisionOrientation;
import ai.fritz.visionposeestimationtflmodel.FritzVisionPoseResult;


public class MainActivity extends LiveCameraActivity {

    private static final String API_KEY = "ENTER YOUR API KEY HERE";

    FritzTFLPosePredictor predictor;
    int imageRotation;
    FritzVisionImage visionImage;
    FritzVisionPoseResult poseResult;

    @Override
    protected void initializeFritz() {
        // TODO: Uncomment this and modify your api key above.
        Fritz.configure(this, API_KEY);
    }

    @Override
    protected void setupPredictor() {
        // STEP 1: Get the predictor and set the options.
        // ----------------------------------------------
        // A FritzOnDeviceModel object is available when a model has been
        // successfully downloaded and included with the app.
        imageRotation = FritzVisionOrientation.getImageRotationFromCamera(this, cameraId);
        predictor = new FritzTFLPosePredictor();
        // ----------------------------------------------
        // END STEP 1
    }

    @Override
    protected void setupImageForPrediction(Image image) {
        // STEP 2: Create the FritzVisionImage object from media.Image
        // ------------------------------------------------------------------------
        visionImage = FritzVisionImage.fromMediaImage(image, imageRotation);
        // ------------------------------------------------------------------------
        // END STEP 2
    }

    @Override
    protected void runInference() {
        // STEP 3: Run predict on the image
        // ---------------------------------------------------
        poseResult = predictor.predict(visionImage);
        // ----------------------------------------------------
        // END STEP 3
    }

    @Override
    protected void showResult(Canvas canvas, Size cameraSize) {
        // STEP 4: Draw the prediction result
        // ----------------------------------
        if(poseResult != null) {
            poseResult.drawPoses(canvas, cameraSize);
        }
        // ----------------------------------
        // END STEP 4
    }
}
