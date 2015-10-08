package io.redshoes.app.navigationdrawer;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

/**
 * Created by vinicius on 9/29/15.
 */
public class NavigationDrawer {

    public static Drawer createNavigationDrawer(final Activity activity, Toolbar toolbar) {
        Drawer drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home"),
                        new SecondaryDrawerItem().withName("Profile"),
                        new SecondaryDrawerItem().withName("Settings")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(activity, ((Nameable) drawerItem).getName().getText(activity), Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                }).build();

        return drawer;
    }

}
