package vnext.android.vmcameraui;

import static android.view.View.*;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {





    // MODES
    public enum CameraMode { PHOTO, VIDEO, PORTRAIT}

    public enum FlashMode { AUTO, ON, OFF}

    public enum TimerMode { OFF, FIVE, TEN}

    public enum VideoResolutionMode { Q2K, QFHD, QHD}

    public enum VideoSizeMode { S9_16, S1_1, SFULL}

    public enum ImageSizeMode { S64MP, S3_4, S9_16, S1_1, SFULL}

    public enum PortraitSizeMode { S3_4, S9_16, S1_1, SFULL}


    //Extended Views
    private View extendedSizeOptionsView;
    private View extendedFlashOptionsView;
    private View extendedResolutionOptionsView;
    private View extendedTimerOptionsView;
    private View extendedFilterOptionsView;


    //Main Views Helper
    private LinearLayout modeDefaultOptionsContainer;
    private FrameLayout compactControlContainer;


    //Surface View
    private View cameraPreviewSurface;


    //Top Level Option Widget
    ImageButton btnSetting;
    ImageButton btnFlash;
    ImageButton btnTimer;
    ImageButton btnSize;
    ImageButton btnFilter;
    ImageButton btnResolution;


    //Extended Mode Size Widget
    ImageButton btnSize64mp;
    ImageButton btnSize3_4;
    ImageButton btnSize9_16;
    ImageButton btnSize1_1;
    ImageButton btnSizeFull;


    //Extended Mode Flash Widget
    ImageButton btnFlashAuto;
    ImageButton btnFlashOn;
    ImageButton btnFlashOff;


    //Extended Mode Timer Widget
    ImageButton btnTimerOff;
    ImageButton btnTimer5sec;
    ImageButton btnTimer10sec;


    //Extended Mode Resolution Widget
    ImageButton btnResolution4k;
    ImageButton btnResolutionFHD;
    ImageButton btnResolutionHD;


    //Extended Mode Filter Widget




//    private View[] topLevelViews;

    private TextView sliderTitle;
    private SeekBar controlSlider;
    private LinearLayout modeListLayout;








    private CameraMode currentCameraMode = CameraMode.PHOTO;
    private FlashMode currentFlashMode = FlashMode.OFF;
    private TimerMode currentTimerMode = TimerMode.OFF;
    private VideoResolutionMode currentVideoResolutionMode = VideoResolutionMode.QFHD;
    private VideoSizeMode currentVideoSizeMode = VideoSizeMode.S9_16;
    private ImageSizeMode currentImageSizeMode = ImageSizeMode.SFULL;
    private PortraitSizeMode currentPortraitSizeMode = PortraitSizeMode.S9_16;


    private View currentTopView = null;
    private boolean isExtendedOptionsVisible = false;
    private boolean isSliderControlVisible = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modeDefaultOptionsContainer = findViewById(R.id.mode_default_options_container);
        compactControlContainer = findViewById(R.id.compact_control_container);
        cameraPreviewSurface = findViewById(R.id.camera_preview_surface); // Find the SurfaceView



        extendedFlashOptionsView = findViewById(R.id.extended_flash_options_view);
        extendedSizeOptionsView = findViewById(R.id.extended_size_options_view);
        extendedResolutionOptionsView = findViewById(R.id.extended_resolution_options_view);
        extendedFilterOptionsView = findViewById(R.id.extended_filter_options_view);
        extendedTimerOptionsView = findViewById(R.id.extended_timer_options_view);


        //Modes
        btnSetting = modeDefaultOptionsContainer.findViewById(R.id.btn_setting);
        btnFlash = modeDefaultOptionsContainer.findViewById(R.id.btn_flash);
        btnTimer = modeDefaultOptionsContainer.findViewById(R.id.btn_timer);
        btnSize = modeDefaultOptionsContainer.findViewById(R.id.btn_size);
        btnFilter = modeDefaultOptionsContainer.findViewById(R.id.btn_filter);
        btnResolution = modeDefaultOptionsContainer.findViewById(R.id.btn_resolution);


        //Correct Implementation
        //Extended Mode Size
        btnSize64mp = findViewById(R.id.btn_size_64mp);
        btnSize3_4 = findViewById(R.id.btn_size_3_4);
        btnSize9_16 = findViewById(R.id.btn_size_9_16);
        btnSize1_1 = findViewById(R.id.btn_size_1_1);
        btnSizeFull = findViewById(R.id.btn_size_full);


        //Extended Mode Flash
        btnFlashAuto = findViewById(R.id.btn_flash_auto);
        btnFlashOn = findViewById(R.id.btn_flash_on);
        btnFlashOff = findViewById(R.id.btn_flash_off);


        //Extended Mode Timer
        btnTimerOff = findViewById(R.id.btn_timer_off);
        btnTimer5sec = findViewById(R.id.btn_timer_five);
        btnTimer10sec = findViewById(R.id.btn_timer_ten);


        //Extended Mode Resolution
        btnResolution4k = findViewById(R.id.btn_resolution_2k);
        btnResolutionFHD = findViewById(R.id.btn_resolution_fhd);
        btnResolutionHD = findViewById(R.id.btn_resolution_hd);


        sliderTitle = findViewById(R.id.slider_title);
        controlSlider = findViewById(R.id.control_slider);

        modeListLayout = findViewById(R.id.mode_list);
        setupModeSelectionListeners();
        attachModeSpecificDefaultOptionListeners();

        cameraPreviewSurface.setOnClickListener(v -> handleMenuDismissal());

        setCameraMode(CameraMode.PHOTO);
    }

    private void handleMenuDismissal() {
        if (isSliderControlVisible) {
            hideSliderControl();
        } else if (isExtendedOptionsVisible) {
            showTopView(modeDefaultOptionsContainer);
        }
    }


    private void setupModeSelectionListeners() {

        //TODO: Improve this Function
        View.OnClickListener modeClickListener = v -> {
            TextView selectedModeTextView = (TextView) v;
            String modeText = selectedModeTextView.getText().toString();
            CameraMode newMode = null;

            for (int i = 0; i < modeListLayout.getChildCount(); i++) {
                View child = modeListLayout.getChildAt(i);
                if (child instanceof TextView) {
                    ((TextView) child).setTextColor(Color.WHITE);
                }
            }

            selectedModeTextView.setTextColor(Color.rgb(173, 216, 230));

            centerSelectedMode(selectedModeTextView); // <--- ADD THIS LINE HERE

            switch (modeText) {
                case "PORTRAIT": newMode = CameraMode.PORTRAIT; break;
                case "VIDEO": newMode = CameraMode.VIDEO; break;
                case "PHOTO": newMode = CameraMode.PHOTO; break;
            }
            if (newMode != null) {
                setCameraMode(newMode);
            }
        };

        findViewById(R.id.mode_portrait).setOnClickListener(modeClickListener);
        findViewById(R.id.mode_video).setOnClickListener(modeClickListener);
        findViewById(R.id.mode_photo).setOnClickListener(modeClickListener);

    }


    private void centerSelectedMode(View selectedView) {
        HorizontalScrollView modeScrollView = findViewById(R.id.mode_scroll_view);

        // Check if the scroll view exists
        if (modeScrollView == null) return;

        // 1. Calculate the center position of the scroll view
        int scrollWidth = modeScrollView.getWidth();
        int centerOfScroll = scrollWidth / 2;

        // 2. Calculate the center position of the selected view relative to the modeListLayout
        int selectedViewWidth = selectedView.getWidth();
        int selectedViewCenter = selectedView.getLeft() + (selectedViewWidth / 2);

        // 3. Determine the final scroll position
        // Target position = (Center of Selected View) - (Center of ScrollView)
        int scrollTo = selectedViewCenter - centerOfScroll;

        // 4. Perform the smooth scroll
        // The scroll will be clipped to the scroll view's bounds automatically
        modeScrollView.smoothScrollTo(scrollTo, 0);
    }

    public void setCameraMode(CameraMode newMode) {
        currentCameraMode = newMode;
        showTopView(modeDefaultOptionsContainer);

        switch (newMode) {
            case PHOTO:
                //Modes
                btnFlash.setVisibility(VISIBLE);
                btnTimer.setVisibility(VISIBLE);
                btnResolution.setVisibility(GONE);
                // Extended
                btnSize64mp.setVisibility(VISIBLE);
                btnSize3_4.setVisibility(VISIBLE);
                break;
            case PORTRAIT:
                btnTimer.setVisibility(VISIBLE);
                btnResolution.setVisibility(GONE);
                btnFlash.setVisibility(GONE);
                // Extended
                btnSize64mp.setVisibility(GONE);
                btnSize3_4.setVisibility(VISIBLE);
                break;
            case VIDEO:
                btnFlash.setVisibility(VISIBLE);
                btnTimer.setVisibility(GONE);
                btnResolution.setVisibility(VISIBLE);
                // Extended
                btnSize64mp.setVisibility(GONE);
                btnSize3_4.setVisibility(GONE);
                break;
            default:
                break;
        }


    }




    private void attachModeSpecificDefaultOptionListeners(){
        if(btnFlash != null) btnFlash.setOnClickListener(v -> showTopView(extendedFlashOptionsView));
        if(btnTimer != null) btnTimer.setOnClickListener(v -> showTopView(extendedTimerOptionsView));
        if(btnSize != null) btnSize.setOnClickListener(v -> showTopView(extendedSizeOptionsView));
        if(btnFilter != null) btnFilter.setOnClickListener(v -> showTopView(extendedFilterOptionsView));
        //if(btnSetting != null) btnSetting.setOnClickListener(v -> showTopView(extendedSetingsOptionsView));
        if(btnResolution != null) btnResolution.setOnClickListener(v -> showTopView(extendedResolutionOptionsView));

        //Extended Mode Size
        if(btnSize64mp != null) btnSize64mp.setOnClickListener(v -> {currentImageSizeMode = ImageSizeMode.S64MP;showTopView(modeDefaultOptionsContainer);});

        if(btnSize3_4 != null) btnSize3_4.setOnClickListener(v -> {
            if(currentCameraMode == CameraMode.PORTRAIT){
                currentPortraitSizeMode = PortraitSizeMode.S3_4;
            }else {
                currentImageSizeMode = ImageSizeMode.S3_4;
            }
            showTopView(modeDefaultOptionsContainer);});

        if(btnSize9_16 != null) btnSize9_16.setOnClickListener(v -> {
            if(currentCameraMode == CameraMode.VIDEO){
            currentVideoSizeMode = VideoSizeMode.S9_16;
        }else if(currentCameraMode == CameraMode.PORTRAIT){
            currentPortraitSizeMode = PortraitSizeMode.S9_16;
        }else {
            currentImageSizeMode = ImageSizeMode.S9_16;
        }
        showTopView(modeDefaultOptionsContainer);});

        if(btnSize1_1 != null) btnSize1_1.setOnClickListener(v -> {
            if(currentCameraMode == CameraMode.VIDEO){
                currentVideoSizeMode = VideoSizeMode.S1_1;
            }else if(currentCameraMode == CameraMode.PORTRAIT){
                currentPortraitSizeMode = PortraitSizeMode.S1_1;
            }else {
                currentImageSizeMode = ImageSizeMode.S1_1;
            }
            showTopView(modeDefaultOptionsContainer);});

        if(btnSizeFull != null) btnSizeFull.setOnClickListener(v -> {
            if(currentCameraMode == CameraMode.VIDEO){
                currentVideoSizeMode = VideoSizeMode.SFULL;
            }else if(currentCameraMode == CameraMode.PORTRAIT){
                currentPortraitSizeMode = PortraitSizeMode.SFULL;
            }else {
                currentImageSizeMode = ImageSizeMode.SFULL;
            }
            showTopView(modeDefaultOptionsContainer);});



        //Extended Mode Flash
        if(btnFlashAuto != null) btnFlashAuto.setOnClickListener(v -> {currentFlashMode = FlashMode.AUTO;showTopView(modeDefaultOptionsContainer);});
        if(btnFlashOn != null) btnFlashOn.setOnClickListener(v -> {currentFlashMode = FlashMode.ON;showTopView(modeDefaultOptionsContainer);});
        if(btnFlashOff != null) btnFlashOff.setOnClickListener(v -> {currentFlashMode = FlashMode.OFF;showTopView(modeDefaultOptionsContainer);});

        //Extended Mode Timer
        if(btnTimerOff != null) btnTimerOff.setOnClickListener(v -> {currentTimerMode = TimerMode.OFF;showTopView(modeDefaultOptionsContainer);});
        if(btnTimer5sec != null) btnTimer5sec.setOnClickListener(v -> {currentTimerMode = TimerMode.FIVE;showTopView(modeDefaultOptionsContainer);});
        if(btnTimer10sec != null) btnTimer10sec.setOnClickListener(v -> {currentTimerMode = TimerMode.TEN;showTopView(modeDefaultOptionsContainer);});


        //Extended Mode Resolution
        if(btnResolution4k != null) btnResolution4k.setOnClickListener(v -> {currentVideoResolutionMode = VideoResolutionMode.Q2K;showTopView(modeDefaultOptionsContainer);});
        if(btnResolutionFHD != null) btnResolutionFHD.setOnClickListener(v -> {currentVideoResolutionMode = VideoResolutionMode.QFHD;showTopView(modeDefaultOptionsContainer);});
        if(btnResolutionHD != null) btnResolutionHD.setOnClickListener(v -> {currentVideoResolutionMode = VideoResolutionMode.QHD;showTopView(modeDefaultOptionsContainer);});


    }

    private void showTopView(View targetView) {
//        for (View view : topLevelViews) {
//            if (view != null) {
//                view.setVisibility(GONE);
//            }
//        }

        if(currentTopView != null){
            currentTopView.setVisibility(GONE);
        }

        if (targetView != null) {
            currentTopView = targetView;
            targetView.setVisibility(VISIBLE);
            isExtendedOptionsVisible = (targetView != modeDefaultOptionsContainer);
        }

        hideSliderControl();
    }

    private void showSliderControl(String title, int max) {
        sliderTitle.setText(title);
        controlSlider.setMax(max);
        compactControlContainer.setVisibility(VISIBLE);
        isSliderControlVisible = true;
    }

    private void hideSliderControl() {
        compactControlContainer.setVisibility(GONE);
        isSliderControlVisible = false;
    }


}