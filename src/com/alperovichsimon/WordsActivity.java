package com.alperovichsimon;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.alperovichsimon.gamemodel.Word;
import com.alperovichsimon.gamemodel.WordsPool;
import com.alperovichsimon.persistence.WordsPersistenceManager;


/**
 * Simon Alperovich
 * Date: 01.10.12
 * Time: 16:54
 */
public class WordsActivity extends Activity {
    private Button plusButton;
    private Button minusButton;
    private Button addButton;
    private EditText wordsText;
    private RadioButton hardRadioButton;
    private RadioButton mediumRadioButton;
    private RadioButton easyRadioButton;
    private TextView wordsCounter;
    private TextView wordsError;

    private final int DEFAULT_WORDS_NUMBER = 10;
    private final int MAX_WORDS_NUMBER = 10000;
    private int currentNumber = DEFAULT_WORDS_NUMBER;
    private static final String TAG = "WordsActivity";
    private Button deleteButton;

    @Override
    protected void onStart() {
        super.onStart();
        wordsText = (EditText) findViewById(R.id.words_number_text);
        minusButton = (Button) findViewById(R.id.minus_words_button);
        plusButton = (Button) findViewById(R.id.plus_words_button);
        addButton = (Button) findViewById(R.id.add_words_button);
        hardRadioButton = (RadioButton) findViewById(R.id.hard_level_button);
        mediumRadioButton = (RadioButton) findViewById(R.id.medium_level_button);
        easyRadioButton = (RadioButton) findViewById(R.id.easy_level_button);
        deleteButton = (Button) findViewById(R.id.delete_words_button);
        wordsCounter = (TextView) findViewById(R.id.word_counter_text);
        wordsError = (TextView) findViewById(R.id.word_error_text);
        prepare();
        update();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words);
        WordsPersistenceManager.getInstance().loadWords(getApplicationContext().getAssets());
    }


    private void prepare() {
        RadioButton button = (RadioButton) findViewById(R.id.hard_level_button);
        button.setChecked(true);

        minusButton.setOnClickListener(minusListener());
        plusButton.setOnClickListener(plusListener());
        wordsText.setFilters(new InputFilter[]{new IntervalFilter(1, MAX_WORDS_NUMBER)});
        wordsText.addTextChangedListener(numberWatcher());
        addButton.setOnClickListener(addButtonListener());
        deleteButton.setOnClickListener(deleteListener());
    }

    private View.OnClickListener deleteListener() {
        return new MyOnClickListener() {
            @Override
            public void doOnClick(View view) {
                WordsPersistenceManager.getInstance().shuffle();
                WordsPool.getInstance().deleteAll();
            }
        };
    }

    private void update() {
        WordsPool pool = WordsPool.getInstance();
        updateWordsText();

        hardRadioButton.setText(getString(R.string.hard_level_text) + " " + pool.getHardNumber());
        mediumRadioButton.setText(getString(R.string.medium_level_text) + " " + pool.getMediumNumber());
        easyRadioButton.setText(getString(R.string.easy_level_text) + " " + pool.getEasyNumber());

        wordsCounter.setText(getString(R.string.word_counter_text) + pool.getWordsNumber());
    }

    private void updateWordsText() {
        StringBuilder builder = new StringBuilder();
        builder.append(currentNumber);
        wordsText.setText(builder.toString());
    }

    private View.OnClickListener minusListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentNumber <= 1) {
                    return;
                }
                --currentNumber;
                updateWordsText();
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
                updateWordsText();
            }
        };
    }

    private TextWatcher numberWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (editable.toString().equals("")) {
                        currentNumber = 1;
                        return;
                    }

                    currentNumber = Integer.parseInt(editable.toString());
                } catch (NumberFormatException e) {
                    Log.d(TAG, "words filter doesn't work", e);
                }
            }
        };
    }

    private View.OnClickListener addButtonListener() {
        return new MyOnClickListener() {
            @Override
            public void doOnClick(View view) {
                if (hardRadioButton.isChecked()) {
                    addWords(Word.Level.HARD);
                } else if (mediumRadioButton.isChecked()) {
                    addWords(Word.Level.MEDIUM);
                } else if (easyRadioButton.isChecked()) {
                    addWords(Word.Level.EASY);
                }
            }
        };
    }

    private void addWords(Word.Level level) {
        for (int i = 0; i != currentNumber; ++i) {
            String nextWord = WordsPersistenceManager.getInstance().getNextWord(level);
            if (nextWord == null) {
                wordsError.setText("В базе больше нет слов такой сложности");
                return;
            }
            WordsPool.getInstance().addWord(new Word(nextWord, level));
        }
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
            try {
                StringBuilder resultString = new StringBuilder();
                resultString.append(dest.toString().substring(0, dstart));
                resultString.append(source.subSequence(start, end));
                resultString.append(dest.toString().substring(dend, dest.length()));

                int result = Integer.parseInt(resultString.toString());

                if (result < this.start || result > this.end) {
                    return "";
                }
                return null;
            } catch (NumberFormatException e) {
                return "";
            }
        }
    }

    private abstract class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            doOnClick(view);
            update();
        }

        public abstract void doOnClick(View view);
    }
}
