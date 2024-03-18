package com.vivek.emi_calculator_app.ui.EMI;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.vivek.emi_calculator_app.R;
import com.vivek.emi_calculator_app.databinding.FragmentHomeBinding;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DecimalFormat;

public class HomeFragment extends Fragment {

    String TAG = com.vivek.emi_calculator_app.ui.EMI.HomeFragment.class.getSimpleName();

    private FragmentHomeBinding binding;
    Double totalAmount = 0.0;
    Double interest = 0.0;
    Double feesCharge = 0.0;
    private PieChart pieChart;

    int loanTerm = 0;
    /*   Double totalAmount = 100000.0, interest = 12.0,  feesCharge = 0.0;
         int loanTerm = 12;
     */
    String pattern = "###,###.##";

    DecimalFormat decimalFormat = new DecimalFormat(pattern);
    RadioButton activeRadioButton;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel HomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.tvResultLoanAmount.setText(decimalFormat.format(totalAmount));
        binding.tvResultFeesAndCharges.setText(decimalFormat.format(feesCharge));
        double emi = 0;
        binding.tvResultEmi.setText(decimalFormat.format(emi));
        double interestAmount=0.0;
        binding.tvResultInterest.setText(decimalFormat.format(interestAmount));
        double totalPayment=0.0;
        binding.tvResultTotalPayment.setText(decimalFormat.format(totalPayment));

        return root;
    }
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @SuppressLint({"ResourceType", "NewApi"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.tvResultLoanAmount.findViewById(R.id.tvResultLoanAmount);
        binding.tvResultFeesAndCharges.findViewById(R.id.tvResultFeesAndCharge);
        binding.tvResultEmi.findViewById(R.id.tvResultEmi);
        binding.tvResultInterest.findViewById(R.id.tvResultInterest);
        binding.tvResultTotalPayment.findViewById(R.id.tvResultTotalPayment);
        binding.btnAllDetail.findViewById(R.id.btn_allDetail);
        pieChart=view.findViewById(R.id.pcPieChart);

        binding.btnAllDetail.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.emiDetailsFragment);
           // Navigation.findNavController(v).navigate(R.id.scrollingFragment);
        });

        binding.edtEnterAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                totalAmount = (s.toString().equals("") || s.toString().isEmpty() ? Double.parseDouble("0.0") : Double.parseDouble(s.toString()));
            }
        });
        binding.edtRateOfInterest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                interest = (s.toString().equals("") || s.toString().isEmpty() ? Double.parseDouble("0.0") : Double.parseDouble(s.toString()));
            }
        });
        binding.edtLoanTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loanTerm = (s.toString().equals("") || s.toString().isEmpty() ? Integer.parseInt("0") : Integer.parseInt(s.toString()));
            }
        });
        binding.edtFeesCharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                feesCharge = (s.toString().equals("") || s.toString().isEmpty() ? Double.parseDouble("0.0") : Double.parseDouble(s.toString()));
            }
        });


        activeRadioButton = binding.rgYearMonth.findViewById(binding.rgYearMonth.getCheckedRadioButtonId());
        binding.rgYearMonth.setOnCheckedChangeListener((group, checkedId) -> activeRadioButton = group.findViewById(checkedId));

        binding.btnCalculate.setOnClickListener(v -> {
            double emi = 0.0;
            double interestAmount;
            double totalPayment;
            if (activeRadioButton.getText().toString().equalsIgnoreCase(getResources().getString(R.string.year))) {
                emi = yearlyLoanEMICalculator(totalAmount, interest, loanTerm, feesCharge);

            } else {
                emi = monthlyLoanEMICalculator(totalAmount, interest, loanTerm, feesCharge);

            }
            // show EMI in tvEMI and tvResultEmi
            binding.tvEmi.setText(decimalFormat.format(emi));
            binding.tvResultEmi.setText(decimalFormat.format(emi));

            // Calculate Interest and Total Payment
            interestAmount = (emi * loanTerm * (activeRadioButton.getText().toString().equalsIgnoreCase(getResources().getString(R.string.year)) ? 12 : 1)) - totalAmount - feesCharge;
            totalPayment = emi * loanTerm * (activeRadioButton.getText().toString().equalsIgnoreCase(getResources().getString(R.string.year)) ? 12 : 1);

            binding.tvResultFeesAndCharges.setText(decimalFormat.format(feesCharge));
            binding.tvResultLoanAmount.setText(decimalFormat.format(totalAmount));
            binding.tvResultInterest.setText(decimalFormat.format(interestAmount));
            binding.tvResultTotalPayment.setText(decimalFormat.format(totalPayment));

            pieChart.clearChart();
            pieChart.addPieSlice(new PieModel("Interest", (float) interestAmount, Color.CYAN));
            pieChart.addPieSlice(new PieModel("Total Payment", (float) totalPayment, Color.GRAY));
            pieChart.startAnimation();

            hideKeyboard(v);
        });

        binding.btnClear.setOnClickListener(v -> clearCalculation());
    }
    private void clearCalculation() {
        binding.edtEnterAmount.setText("");
        binding.edtRateOfInterest.setText("");
        binding.edtLoanTerm.setText("");
        binding.edtFeesCharge.setText("");
        binding.tvEmi.setText("");
        binding.tvResultEmi.setText("");
        binding.tvResultLoanAmount.setText("");
        binding.tvResultFeesAndCharges.setText("");
        binding.tvResultInterest.setText("");
        binding.tvResultTotalPayment.setText("");
    }

    private Double yearlyLoanEMICalculator(double loanAmount, double annualInterestRate, int loanTermInYears, double feesAndCharges) {

        System.out.println("Enter the loan amount: " + loanAmount);
        System.out.println("Enter the annual interest rate (in percentage): " + annualInterestRate);
        System.out.println("Enter the loan term (in years): " + loanTermInYears);
        System.out.println("Enter any additional fees and extra charges: " + feesAndCharges);

        double monthlyInterestRate = annualInterestRate / 12 / 100;
        int numberOfPayments = loanTermInYears * 12;

        // Calculate EMI using the formula
        double emi = (loanAmount + feesAndCharges) * monthlyInterestRate *
                Math.pow(1 + monthlyInterestRate, numberOfPayments) /
                (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);

        System.out.println("Monthly EMI: " + emi);
        return emi;
    }

    private Double monthlyLoanEMICalculator(double loanAmount, double annualInterestRate, int loanTermInMonths, double feesAndCharges) {
        System.out.println("Enter the loan amount: " + loanAmount);
        System.out.println("Enter the annual interest rate (in percentage): " + annualInterestRate);
        System.out.println("Enter the loan term (in months): " + loanTermInMonths);
        System.out.println("Enter any additional fees and extra charges: " + feesAndCharges);

        double monthlyInterestRate = annualInterestRate / 12 / 100;

        // Calculate EMI using the formula
        double emi = (loanAmount + feesAndCharges) * monthlyInterestRate *
                Math.pow(1 + monthlyInterestRate, loanTermInMonths) /
                (Math.pow(1 + monthlyInterestRate, loanTermInMonths) - 1);

        System.out.println("Monthly EMI: " + emi);
        return emi;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}







