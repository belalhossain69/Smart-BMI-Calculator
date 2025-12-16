package com.example.smartbmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    TextView tvHeight, tvWeight, tvAge;
    SeekBar seekHeight;
    AppCompatImageButton btnWeightPlus, btnWeightMinus, btnAgePlus, btnAgeMinus;
    Button btnCalculate;

    int weight = 60;
    int age = 22;
    int height = 170;
    boolean isMaleSelected = false;
    boolean isFemaleSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(Color.parseColor("#0F1220"));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            getWindow().getDecorView().setSystemUiVisibility(0);
        }


        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_background));

        }



        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvAge = findViewById(R.id.tvAge);
        seekHeight = findViewById(R.id.seekHeight);

        btnWeightPlus = findViewById(R.id.btnWeightPlus);
        btnWeightMinus = findViewById(R.id.btnWeightMinus);
        btnAgePlus = findViewById(R.id.btnAgePlus);
        btnAgeMinus = findViewById(R.id.btnAgeMinus);
        btnCalculate = findViewById(R.id.btnCalculate);
        CardView cardMale = findViewById(R.id.cardMale);
        CardView cardFemale = findViewById(R.id.cardFemale);





        tvWeight.setText(String.valueOf(weight));
        tvAge.setText(String.valueOf(age));
        tvHeight.setText(height + " cm");
        seekHeight.setProgress(height);

        cardMale.setOnClickListener(v -> {
            if(isMaleSelected) {

                isMaleSelected = false;
                cardMale.setCardBackgroundColor(Color.parseColor("#1D1E33"));
            } else {

                isMaleSelected = true;
                isFemaleSelected = false;
                cardMale.setCardBackgroundColor(Color.parseColor("#E91E63"));
                cardFemale.setCardBackgroundColor(Color.parseColor("#1D1E33"));
            }
        });

        cardFemale.setOnClickListener(v -> {
            if(isFemaleSelected) {

                isFemaleSelected = false;
                cardFemale.setCardBackgroundColor(Color.parseColor("#1D1E33"));
            } else {

                isFemaleSelected = true;
                isMaleSelected = false;
                cardFemale.setCardBackgroundColor(Color.parseColor("#E91E63"));
                cardMale.setCardBackgroundColor(Color.parseColor("#1D1E33"));
            }
        });


        seekHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                height = progress;
                if (height < 50) height = 50;
                tvHeight.setText(height + " cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        btnWeightPlus.setOnClickListener(v -> {
            weight++;
            tvWeight.setText(String.valueOf(weight));
        });

        btnWeightMinus.setOnClickListener(v -> {
            if(weight > 1) weight--;
            tvWeight.setText(String.valueOf(weight));
        });


        btnAgePlus.setOnClickListener(v -> {
            age++;
            tvAge.setText(String.valueOf(age));
        });

        btnAgeMinus.setOnClickListener(v -> {
            if(age > 1) age--;
            tvAge.setText(String.valueOf(age));
        });


        btnCalculate.setOnClickListener(v -> {
            double heightMeters = height / 100.0;
            double bmi = weight / (heightMeters * heightMeters);
            String bmiResult = String.format("%.2f", bmi);

            String message = "";
            int iconColor = 0xFFFFFFFF;
            if(bmi < 18.5){
                message = "Underweight";
                iconColor = 0xFF64B5F6;
            } else if(bmi < 24.9){
                message = "Normal";
                iconColor = 0xFF81C784;
            } else if(bmi < 29.9){
                message = "Overweight";
                iconColor = 0xFFFFB74D;
            } else{
                message = "Obese";
                iconColor = 0xFFE57373;
            }


            LayoutInflater inflater = getLayoutInflater();
            View toastView = inflater.inflate(R.layout.toast_bmi, null);


            ImageView ivBmiIcon = toastView.findViewById(R.id.ivBmiIcon);
            TextView tvBmiResultMessage = toastView.findViewById(R.id.tvBmiResultMessage);

            tvBmiResultMessage.setText("BMI " + bmiResult + " - " + message);

            Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
            ivBmiIcon.startAnimation(pulse);

            final Toast toast = new Toast(this);
            toast.setView(toastView);
            toast.setDuration(Toast.LENGTH_SHORT);


            toast.setGravity(Gravity.BOTTOM | Gravity.BOTTOM, 0, 170);
            toast.show();

        });

    }
}
