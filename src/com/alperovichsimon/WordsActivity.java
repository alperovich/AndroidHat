package com.alperovichsimon;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * Simon Alperovich
 * Date: 01.10.12
 * Time: 16:54
 */
public class WordsActivity extends Activity {
    private Button plusButton;
    private Button minusButton;
    private TextView wordsText;

    private final int DEFAULT_WORDS_NUMBER = 10;
    private final int MAX_WORDS_NUMBER = 10000;
    private int currentNumber = DEFAULT_WORDS_NUMBER;

    @Override
    protected void onStart() {
        super.onStart();
        wordsText = (TextView) findViewById(R.id.words_number_text);
        minusButton = (Button) findViewById(R.id.minus_words_button);
        plusButton = (Button) findViewById(R.id.plus_words_button);
        prepare();
        update();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words);


    }


    private void prepare() {
        RadioButton button = (RadioButton) findViewById(R.id.hard_level_button);
        button.setChecked(true);

        minusButton.setOnClickListener(minusListener());
        plusButton.setOnClickListener(plusListener());
        wordsText.setFilters(new InputFilter[]{new IntervalFilter(1, MAX_WORDS_NUMBER)});
      //  wordsText.setOnEditorActionListener(counterListener());
    }

    private void update() {
        StringBuilder builder = new StringBuilder();
        builder.append(currentNumber);
        wordsText.setText(new StringBuffer(builder));
    }

    private View.OnClickListener minusListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentNumber <= 0) {
                    return;
                }
                --currentNumber;
                update();
            }
        };
    }

    private View.OnClickListener plusListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentNumber >= MAX_WORDS_NUMBER) {
                    return;
                }
                ++currentNumber;
                update();
            }
        };
    }

    private TextView.OnEditorActionListener counterListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String curText = textView.getText().toString();
                    if (curText.length() == 0) {
                        return false;
                    }
                    int wordsNum = Integer.parseInt(curText);
                    if (Integer.parseInt(textView.getText().toString()) > MAX_WORDS_NUMBER) {
                        return false;
                    } else {
                        currentNumber = wordsNum;
                    }
                    return true;
                }
                update();
                return false;
            }
        };
    }


    private static class IntervalFilter implements InputFilter {
        int start;
        int end;

        public IntervalFilter(int start, int end) {
            this.start = start;
            this.end = end;
        }


        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try{
                StringBuilder resultString = new StringBuilder();
                resultString.append(dest.toString().substring(0, dstart));
                resultString.append(source.subSequence(start, end));
                resultString.append(dest.toString().substring(dend, dest.length()));

                if (resultString.toString().equals("")){
                    return null;
                }

                int result = Integer.parseInt(resultString.toString());

                if (result < this.start || result > this.end){
                    return "";
                }
                return null;
            } catch (NumberFormatException e){
                return "";
            }
        }
    }
}
