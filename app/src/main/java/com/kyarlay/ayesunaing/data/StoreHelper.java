package com.kyarlay.ayesunaing.data;

import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.TownShip;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;

import java.util.ArrayList;

public class StoreHelper implements ConstantVariable{


    public static int getStoreIdbyTownShipId(AppCompatActivity activity, int id){

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(activity);

        int returnValue = -1;
        ArrayList<TownShip> townShipArrayList = databaseAdapter.getTownShipByList();

        for (int j=0; j < townShipArrayList.size(); j++){
            if (townShipArrayList.get(j).getId() == id){

                returnValue = townShipArrayList.get(j).getStore_location_id();

            }
        }
        return returnValue;

    }




    public static void saveDeliverPreferences(AppCompatActivity activity, int id){
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(activity);
        ArrayList<Delivery> deliveryList = databaseAdapter.getDelivery();
        MyPreference prefs = new MyPreference(activity);

        for (int j = 0; j < deliveryList.size(); j++) {
            if (deliveryList.get(j).getId() == id ) {

                prefs.saveIntPerferences(DELIVERY_ID, deliveryList.get(j).getId());
                prefs.saveIntPerferences(DELIVERY_PRICE, deliveryList.get(j).getPrice());
                prefs.saveIntPerferences(DELIVERY_FREE_AMOUNT, deliveryList.get(j).getFreeDelivery());


            }
        }
    }


}
