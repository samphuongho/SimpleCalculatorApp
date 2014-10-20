//http://www.instructables.com/id/How-to-Create-an-Android-Application/step5/Adding-logic-to-calculate-interest-and-display-to-/
//http://javatechig.com/android/android-seekbar-example
package edu.csulb.android.lab2f;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity 
{
	Button button;
	EditText borrowedAmt;
	SeekBar interestRate;
	RadioGroup loanTerm;
	RadioButton termChosen;
	CheckBox taxAndInsur; 
	TextView monthlyPay;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        borrowedAmt = (EditText) findViewById(R.id.editText1);
        loanTerm = (RadioGroup) findViewById(R.id.radioGroup1);
        monthlyPay = (TextView) findViewById(R.id.textView4);
        taxAndInsur = (CheckBox) findViewById(R.id.checkBox1);
        interestRate = (SeekBar) findViewById(R.id.seekBar1);
        button = (Button) findViewById(R.id.button1);
        
        // SeekBar
        interestRate.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
        {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				progressChanged = progress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) 
			{
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) 
			{
				Toast.makeText(MainActivity.this,"Rate chosen = " + progressChanged, 
						Toast.LENGTH_SHORT).show();
			}
		});
        
        //Button
        button.setOnClickListener(new View.OnClickListener()
    	{
        	public void onClick(View arg0)
    		{
    			double borrowedAmtValue = 0;
    		    float rateValue = 0;
    		    int noOfLoanMonth = 0;
    		    double taxAndInsurValue = 0;
    		    double monthlyPayValue = 0;
    		
    		    // Borrowed amount processing
    		    borrowedAmtValue = Double.parseDouble(borrowedAmt.getText().toString());
    		    	
    		    // Interest Rate processing
    		    rateValue = ((float)interestRate.getProgress() / 1200);
    		    
    		    // Loan Term processing
    		    int[] termGroup = {180, 240, 360};
    		    int selectedTerm = loanTerm.getCheckedRadioButtonId();
    		    int selectedTermIndex = 0;
    		    switch(selectedTerm){
    		    case R.id.radio0:
    		        selectedTermIndex = 0;
    		        break;
    		    case R.id.radio1:
    		        selectedTermIndex = 1;
    		        break;
    		    case R.id.radio2:
    		        selectedTermIndex = 2;
    		        break;
    		    }
    		    noOfLoanMonth = termGroup[selectedTermIndex];
    			
    		    // Tax and Insurance Included processing
    		    if (taxAndInsur.isChecked()) 
    		    	taxAndInsurValue = (borrowedAmtValue * 0.001);
    			else 
    				taxAndInsurValue = 0;
    		    	
    		    //Calculating total interest value 
    		    double a = 0, b = 0;
    		    if (interestRate.getProgress() == 0)
    		    	monthlyPayValue = ((borrowedAmtValue / noOfLoanMonth) + taxAndInsurValue);
    		    else
    		    {

    		    	monthlyPayValue = (float) ((borrowedAmtValue * rateValue)
    		    			/ (1 - Math.pow(1 + rateValue, - noOfLoanMonth)) + taxAndInsurValue);
    		    }
    		    
    		    // setting the result into the textview's text property to display to the user
    		    monthlyPay.setText(Double.toString(monthlyPayValue));
    		    //txtVwCalculatedInterest.setBackgroundColor(Color.parseColor("#FFCDEF"));
    		    	
    		    // resetting borrowed amount
    		    //borrowedAmt.setText("");
    		}
        });
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
