package com.pemdas.math.pemdasgame;

//import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.lang.Object;
import android.view.KeyEvent;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Interpreter;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.Random;
import java.util.ArrayList;

public class ActiveGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_game);
        startGame();
    }

    public void startGame()
    {

        String equationToDisplay = new String();

        boolean p = getIntent().getExtras().getBoolean("pBool");
        boolean e = getIntent().getExtras().getBoolean("eBool");
        boolean m = getIntent().getExtras().getBoolean("mBool");
        boolean d = getIntent().getExtras().getBoolean("dBool");
        boolean a = getIntent().getExtras().getBoolean("aBool");
        boolean s = getIntent().getExtras().getBoolean("sBool");
        final EditText answerText = (EditText) findViewById(R.id.editAnswerText);
        final TextView equationDisplay = (TextView)findViewById(R.id.textViewEquation);
        final Button submitButton = (Button)findViewById(R.id.submitButton);

        int round = 0;
        int equationSet = 10;
        /*while(round != 10)
        {
            for (int i = 0; i < equationSet; i++)
            {*/
                equationToDisplay = generateEquation(p, e, m, d, a, s, round);
                equationDisplay.setText(equationToDisplay);

                answerText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionID, KeyEvent event){
                        if(actionID == EditorInfo.IME_ACTION_DONE){
                            submitButton.performClick();
                        }
                        return false;
                    }
                });

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitAnswer(equationDisplay);
                    }
                });
            /*}

            ++round;
        }*/
    }

    public String generateEquation(boolean p, boolean e, boolean m, boolean d, boolean a, boolean s, int round)
    {
        String equation = new String();
        String multiplicationString = new String();
        String divisionString = new String();
        String additionString = new String();
        String subtractionString = new String();
        String exponentString = new String();
        int endNumber = 0;

        if(m == true)
        {
            multiplicationString = multiplicationFunc(round);
        }

        if(d == true)
        {
            divisionString = divisionFunc(round);
        }

        if(a == true)
        {
            additionString = additionFunc(round);
        }

        if(s == true)
        {
            subtractionString = subtractionFunc(round);
        }

        if(e == true)
        {
            exponentString = exponentFunc();
        }

        endNumber = generateRand(round);

        equation = combineEquation(multiplicationString, divisionString, additionString, subtractionString, exponentString, endNumber);

        if(p == true)
        {
            //pick 2 random
            //pass whole equation, make it so first parenth cannot come just after an int, and second can't come before first parenth or an int
            equation = parenthFunc(equation);
        }

        return equation;
    }

    public int generateRand(int round)
    {
        Random r = new Random();
        int rand = r.nextInt(9) + 1;
        return rand;
    }

    public String multiplicationFunc(int round)
    {
        int randNum = generateRand(round);
        String multiplicationString = new String();

        multiplicationString = multiplicationString.concat(Integer.toString(randNum));
        multiplicationString = multiplicationString.concat("*");

        return multiplicationString;
    }

    public String divisionFunc(int round)
    {

        int randNum = generateRand(round);
        String divisionString = new String();

        divisionString = divisionString.concat(Integer.toString(randNum));
        divisionString = divisionString.concat("/");

        return divisionString;
    }

    public String additionFunc(int round)
    {

        int randNum = generateRand(round);
        String additionString = new String();

        additionString = additionString.concat(Integer.toString(randNum));
        additionString = additionString.concat("+");

        return additionString;
    }

    public String subtractionFunc(int round)
    {

        int randNum = generateRand(round);
        String subtractionString = new String();

        subtractionString = subtractionString.concat(Integer.toString(randNum));
        subtractionString = subtractionString.concat("-");

        return subtractionString;
    }

    public String exponentFunc()
    {
        int randNum = 0;
        randNum = generateRand(1);

        String exponentString = new String();

        exponentString = exponentString.concat("^");
        exponentString = exponentString.concat(Integer.toString(randNum));

        return exponentString;
    }

    public String parenthFunc(String equation)
    {
        char charCheck1 = 'z';
        char charCheck2 = 'z';
        String parenth1 = "(";
        String parenth2 = ")";
        //decide where to place parantheses
        //make it so first parenth cannot come just after an int, and second can't come before first parenth or an int
        //the parenth cannot surround a single integer, they must be different
        int randFirstParenth = 0;
        int randSecondParenth = 0;
        Random r = new Random();
        int firstParenthRange = 1;

        if(equation.length() - 1 > 2) {
            firstParenthRange = equation.length() - 1;

            while (!(charCheck1 >= '0' && charCheck1 <= '9') /*&& (randFirstParenth == equation.length() - 1)*/) {
                randFirstParenth = r.nextInt(firstParenthRange);
                charCheck1 = equation.charAt(randFirstParenth);
            }

           /* while (!(charCheck >= '0' && charCheck <= '9') && randSecondParenth <= randFirstParenth) {
                randSecondParenth = r.nextInt(equation.length());
                charCheck = equation.charAt(randSecondParenth);
            }*/


            equation = new StringBuilder(equation).insert(randFirstParenth, parenth1).toString();
            //equation = new StringBuilder(equation).insert(randSecondParenth, parenth2).toString();

            while(!(charCheck2 >= '0' && charCheck2 <= '9'))
            {
                randSecondParenth = r.nextInt(equation.length());

                if(randSecondParenth > randFirstParenth+1 && !(randFirstParenth == 0 && randSecondParenth == (equation.length() - 1))) {
                    charCheck2 = equation.charAt(randSecondParenth);
                }
            }

            equation = new StringBuilder(equation).insert(randSecondParenth + 1, parenth2).toString();

        }
        else
        {
            while (!(charCheck1 >= '0' && charCheck1 <= '9'))
            {
                randFirstParenth = r.nextInt(equation.length());
                charCheck1 = equation.charAt(randFirstParenth);
            }

            /*while (!(charCheck >= '0' && charCheck <= '9') && (randSecondParenth < randFirstParenth))
            {
                randSecondParenth = r.nextInt(equation.length());
                charCheck = equation.charAt(randSecondParenth);
            }*/

            equation = new StringBuilder(equation).insert(randFirstParenth, parenth1).toString();
            //equation = new StringBuilder(equation).insert((randSecondParenth + 2), parenth2).toString();

            while(!(charCheck2 >= '0' && charCheck2 <= '9'))
            {
                randSecondParenth = r.nextInt(equation.length());

                if(randSecondParenth > randFirstParenth) {
                    charCheck2 = equation.charAt(randSecondParenth);
                }
            }

            equation = new StringBuilder(equation).insert(randSecondParenth + 1, parenth2).toString();

        }


        return equation;
    }

    public String combineEquation(String multiplicationString, String divisionString, String additionString, String subtractionString, String exponentString, int endNumber)
    {
        String combinedEquation = new String();
        Random r = new Random();
        char charCheck = 'z';
        int randNum = 0;
        ArrayList<String> equationArray = new ArrayList<String>();
        equationArray.add(multiplicationString);
        equationArray.add(divisionString);
        equationArray.add(additionString);
        equationArray.add(subtractionString);
        //equationArray.add(exponentString);

        while(equationArray.size() != 0)
        {
            randNum = r.nextInt(equationArray.size());

            combinedEquation = combinedEquation.concat(equationArray.get(randNum));
            equationArray.remove(randNum);

        }

        combinedEquation = combinedEquation.concat(Integer.toString(endNumber));

        while(!(charCheck >= '0' && charCheck <= '9')) {
            randNum = r.nextInt(combinedEquation.length());
            charCheck = combinedEquation.charAt(randNum);

        }

        combinedEquation = new StringBuilder(combinedEquation).insert((randNum + 1),exponentString).toString();



        return combinedEquation;
    }

    public void submitAnswer(final TextView equationText)
    {
        ContextFactory factory = new ContextFactory();
        Context rhino = factory.enterContext();
        rhino.setOptimizationLevel(-1);
        rhino.setLanguageVersion(Context.VERSION_1_8);
        String result;
        String equation = (String) equationText.getText();

        Scriptable scope = rhino.initStandardObjects();

        factory.call(new ContextAction() {
            @Override
            public Object run(Context rhino) {
                String equation = (String) equationText.getText();
                Scriptable scope = rhino.initStandardObjects();
                rhino.evaluateString(scope, equation, "Javascript", 1, null).toString();
                String result = rhino.evaluateString(scope, equation, "Javascript", 1, null).toString();
                //equationText.setText("hello");
                equationText.setText(result);
                return null;
            }
        });
       /* try {
            String result = rhino.evaluateString(scope, equation, "Javascript", 1, null).toString();
            equationText.setText("hello");
            //equationText.setText(result);
        } finally {
            Context.exit();

        }*/

        //eval(equationTxt);


    }
}

//Best way to do this: