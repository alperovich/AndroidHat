package com.alperovichsimon;

import android.app.Activity;
import android.os.Bundle;


/**
 * Simon Alperovich
 * Date: 01.10.12
 * Time: 16:54
 */
public class WordsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words);

       /* NumberPicker numPicker = (NumberPicker) findViewById(R.id.wordsNumberPicker);
        numPicker.setMaxValue(50);
        numPicker.setMinValue(0);*/
    }

}
