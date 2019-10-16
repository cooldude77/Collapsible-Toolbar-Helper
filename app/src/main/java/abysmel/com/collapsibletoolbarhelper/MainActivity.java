package abysmel.com.collapsibletoolbarhelper;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import abysmel.com.collapsibletoolbarhelper.widgets.MetaballMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        MetaballMenu menu = (MetaballMenu) findViewById(R.id.metaball_menu);

        menu.setElevationRequired(true);

    }

}
