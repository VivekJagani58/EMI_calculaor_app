package com.vivek.emi_calculator_app.ui.loanCompare;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.vivek.emi_calculator_app.R;
import com.vivek.emi_calculator_app.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    EditText edtcmpamount1 , edtcmpinterest1, edtcmploanterm1,edtcmpamount2,edtcmpinterest2,edtcmploanterm2;
    TextView tvCmpEmi1,tvCmpEmi2,tvCmpTotalInterest1,tvCmpTotalInterest2;
    Button btncmpcalculate,btncmpclear;
    RadioButton Cbtnyear1,Cbtnmonth1,Cbtnyear2,Cbtnmonth2;


    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        edtcmpamount1= root.findViewById(R.id.edtcmpamount1);
        edtcmpinterest1=root.findViewById(R.id.edtcmpinterest1);
        tvCmpEmi1=root.findViewById(R.id.tvCmpEmi1);
        edtcmploanterm1=root.findViewById(R.id.edtcmploanterm1);
        tvCmpTotalInterest1=root.findViewById(R.id.tvCmpTotalInterest1);
        edtcmpamount2= root.findViewById(R.id.edtcmpamount2);
        edtcmpinterest2=root.findViewById(R.id.edtcmpinterest2);
        tvCmpEmi2=root.findViewById(R.id.tvCmpEmi2);
        edtcmploanterm2=root.findViewById(R.id.edtcmploanterm2);
        tvCmpTotalInterest2=root.findViewById(R.id.tvCmpTotalInterest2);
        btncmpcalculate=root.findViewById(R.id.btncmpcalculate);
        btncmpclear=root.findViewById(R.id.btncmpclear);
        Cbtnyear1=root.findViewById(R.id.Cbtnyear1);
        Cbtnmonth1=root.findViewById(R.id.Cbtnmonth1);
        Cbtnyear2=root.findViewById(R.id.Cbtnyear2);
        Cbtnmonth2=root.findViewById(R.id.Cbtnmonth2);

        btncmpclear.setOnClickListener(view -> emiClear());
        btncmpcalculate.setOnClickListener(view -> emiCalculation());
        Cbtnyear1.setOnClickListener(view -> {
        });
        Cbtnmonth1.setOnClickListener(view -> {
        });
        Cbtnyear2.setOnClickListener(view -> {
        });
        Cbtnmonth2.setOnClickListener(view -> {
        });




        return root;
    }

    private void emiCalculation() {
        String amount1 = edtcmpamount1.getText().toString();
        String rate1 = edtcmpinterest1.getText().toString();
        String loanTerm1 = edtcmploanterm1.getText().toString();
        String amount2 = edtcmpamount2.getText().toString();
        String rate2 = edtcmpinterest2.getText().toString();
        String loanTerm2 = edtcmploanterm2.getText().toString();
        if (TextUtils.isEmpty(amount1)) {
            edtcmpamount1.setError("Enter Loan Amount");
        }
        if (TextUtils.isEmpty(rate1)) {
            edtcmpinterest1.setError("Enter Rate");
        }
        if (TextUtils.isEmpty(loanTerm1)) {
            edtcmploanterm1.setError("Enter Term");
        }
        if (TextUtils.isEmpty(amount2)) {
            edtcmpamount2.setError("Enter Loan Amount");
        }
        if (TextUtils.isEmpty(rate2)) {
            edtcmpinterest2.setError("Enter Rate");
        }
        if (TextUtils.isEmpty(loanTerm2)) {
            edtcmploanterm2.setError("Enter Term");
        }



    }
    private void emiClear() {
        edtcmpamount1.setText("");
        edtcmpinterest1.setText("");
        tvCmpEmi1.setText("");
        edtcmploanterm1.setText("");
        tvCmpTotalInterest1.setText("");
        edtcmpamount2.setText("");
        edtcmpinterest2.setText("");
        tvCmpEmi2.setText("");
        edtcmploanterm2.setText("");
        tvCmpTotalInterest2.setText("");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}