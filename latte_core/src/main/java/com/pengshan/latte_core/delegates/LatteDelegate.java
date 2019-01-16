package com.pengshan.latte_core.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public abstract class LatteDelegate extends PermissionCheckerDelegate{


    public <T extends LatteDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
