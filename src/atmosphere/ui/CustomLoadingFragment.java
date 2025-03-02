package atmosphere.ui;

import mindustry.ui.fragments.LoadingFragment;

public class CustomLoadingFragment extends LoadingFragment
{
    public boolean allowed = true;

    @Override
    public void show(String text)
    {
        if (allowed) super.show(text);
    }
}
