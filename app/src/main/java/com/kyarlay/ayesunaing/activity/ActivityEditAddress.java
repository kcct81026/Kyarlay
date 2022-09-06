package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Addresses;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.TownShip;
import com.kyarlay.ayesunaing.object.TownShipObject;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalStepAdapter;
import com.rakshakhegde.stepperindicator.StepperIndicator;

import java.util.ArrayList;
import java.util.List;

public class ActivityEditAddress extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "ActivityEditAddress";

    MyPreference prefs;
    AppCompatActivity activity;
    Resources resources;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    List<Delivery> deliveryList = new ArrayList<>();
    DatabaseAdapter databaseAdapter;
    UniversalStepAdapter universalAdapter;
    RecyclerView.LayoutManager manager;

    LinearLayout back_layout;
    RelativeLayout relativeCart;
    CustomTextView title, txtContinue;
    RecyclerView recyclerView;
    StepperIndicator stepper_indicator;
    List<Addresses> addList = new ArrayList<>();
    boolean isMore = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_step_one_cart);

        activity = ActivityEditAddress.this;
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        databaseAdapter = new DatabaseAdapter(activity);


        back_layout = findViewById(R.id.back_layout);
        title = findViewById(R.id.title);
        txtContinue = findViewById(R.id.txtContinue);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        stepper_indicator = findViewById(R.id.stepper_indicator);
        relativeCart = findViewById(R.id.relativeCart);

        manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);

        universalAdapter = new UniversalStepAdapter(activity, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        relativeCart.setVisibility(View.GONE);

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        settingList();
    }

    private void settingList(){
        List<Addresses> addressesList = databaseAdapter.getCustomerAddressList();
        TownShipObject universalPost = new TownShipObject();
        universalPost.setPrid(0);
        universalPost.setPostType(EDIT_ADDRESS_TITLE);
        mainCatDetails.add(universalPost);

        Log.e(TAG, "settingList: ********************* " + addressesList.size() );

        if (addressesList.size() > 0){
            for (int i = 0; i < addressesList.size(); i++) {

                Addresses addresses = addressesList.get(i);


                addresses.setCheckable(false);
                addresses.setSelected(false);
                addresses.setPostType(STEP_ONE_ITEM);

                mainCatDetails.add(addresses);
            }
        }

        ArrayList<TownShip> townShipList = databaseAdapter.getTownShipByList();

        if (townShipList.size() > 0){
            TownShipObject universalPost1 = new TownShipObject();
            universalPost1.setPrid(1);
            universalPost1.setPostType(EDIT_ADDRESS_TITLE);
            mainCatDetails.add(universalPost1);

            universalAdapter.notifyDataSetChanged();
        }

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e(TAG, "onResume: *******************************************  "   + prefs.getBooleanPreference(TEMP_ADD_ADDRESS) );
    }
}
