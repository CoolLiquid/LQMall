package com.simplexx.wnp.util.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wnp on 2018/7/5.
 */

public class PermissionRequestHelper {


    public void request(String[] permissionString, AttemptRequestCallBack[] callBacks) {
        boolean attempResult = true;
        if (callBacks != null) {
            for (AttemptRequestCallBack callBack : callBacks) {
                attempResult = callBack.havePermisionToVisit();
            }
        }

        if (!attempResult) {

        }
    }

    public void request(HashMap<String, AttemptRequestCallBack> permission) {
        if (permission != null) {
            Iterator<String> iterator = permission.keySet().iterator();
            List<String> needRequestPermission = null;
            if (iterator != null) {
                while (iterator.hasNext()) {
                    String permissionName = iterator.next();
                    AttemptRequestCallBack callBack = permission.get(permissionName);
                    if (!callBack.havePermisionToVisit()) {
                        if (needRequestPermission == null) {
                            needRequestPermission = new ArrayList<>();
                        }
                        needRequestPermission.add(permissionName);
                    }
                }
                if (needRequestPermission != null
                        && needRequestPermission.size() > 0) {

                }
            }
        }
    }

    private void realRequest(String[] permissionName) {

    }
}
