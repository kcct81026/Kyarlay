package com.kyarlay.ayesunaing.object;

import com.kyarlay.ayesunaing.data.ConstantVariable;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class MainGrid extends UniversalPost implements ConstantVariable {
    String[] titles = {"Diapers", "Boys", "Girls", "Chairs", "Toys", "Feeding", "Bath", "Reading", "Others"};
 /*   int [] images = {R.mipmap.diaper_icon, R.mipmap.boy_icon, R.mipmap.girl_icon,
                    R.mipmap.baby_gear_icon, R.mipmap.toy_icon, R.mipmap.girl_icon,
            R.mipmap.baby_gear_icon, R.mipmap.toy_icon, R.mipmap.others };
*/

    private String MAIN_GRIDVIEW_ITEM = "MAIN_GRIDVIEW_ITEM";
    private String CATEGORYF_DETAIL_ITEM = "CATEGORYF_DETAIL_ITEM";
    int image;
    String title;

    public MainGrid() {
    }

    public MainGrid(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   /* public ArrayList<UniversalPost> mainGrids (){
        ArrayList<UniversalPost> mainGrids = new ArrayList<>();
        for(int i= 0; i < titles.length; i++){
            MainGrid mainGrid = new MainGrid();
            mainGrid.setImage(images[i]);
            mainGrid.setTitle(titles[i]);
            mainGrid.setPostType(MAIN_GRIDVIEW_ITEM);
            mainGrids.add(mainGrid);
        }

        return mainGrids;
    }*/



}
