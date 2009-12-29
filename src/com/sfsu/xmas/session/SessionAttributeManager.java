package com.sfsu.xmas.session;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SessionAttributeManager {

    public static boolean isAdmin(HttpServletRequest request) {

        //To facilate XMAS use, at least until the paper gets published SET ADMIN = TRUE always
        /*
        String cookieKey = SessionAttributes.ADMIN;
        Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
        if (cookie != null) {
            return cookie.getValue().equals(SessionAttributes.PASS_SESSION_VALUE);
        } else {
            // Not set - default value
            return false;
        }
        */
        return true;
    }

    public static boolean isPreserved(HttpServletRequest request) {
        String cookieKey = SessionAttributes.PRESERVED;
        Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
        if (cookie != null) {
            return Boolean.valueOf(cookie.getValue());
        } else {
            // Not set - default value
            return true;
        }
    }

    public static boolean isComparative(HttpServletRequest request) {
        String cookieKey = SessionAttributes.IMAGE_TYPE_COMPARATIVE;
        Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
        if (cookie != null) {
            return Boolean.valueOf(cookie.getValue());
        } else {
            // Not set - default value
            return false;
        }
    }

    public static boolean isSubtractive(HttpServletRequest request) {
        String cookieKey = SessionAttributes.IMAGE_TYPE_SUBTRACTIVE;
        Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
        if (cookie != null) {
            return Boolean.valueOf(cookie.getValue());
        } else {
            // Not set - default value
            return false;
        }
    }

    public static boolean isDataSelector(HttpServletRequest request) {
        String cookieKey = SessionAttributes.IMAGE_TYPE_DATASELECTOR;
        Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
        if (cookie != null) {
            return Boolean.valueOf(cookie.getValue());
        } else {
            // Not set - default value
            return false;
        }
    }

    /**
     * 
     * @param request
     * @return TRUE if set to true, or attribute not set, false only is secondary active
     */
    public static boolean isPrimaryExpressionDatabaseActive(HttpServletRequest request) {
        String cookieKey = SessionAttributes.PRIMARY_EXPRESSION_DATABASE_ACTIVE;
        Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
        if (cookie != null) {

            // Could check which DB is in focus.
            return Boolean.valueOf(cookie.getValue());
        } else {
            // Not set - default value
            return true;
        }
    }

    public static boolean isTrajectoryVisualization(HttpServletRequest request) {
        String cookieKey = SessionAttributes.IMAGE;
        Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
        if (cookie != null) {
            String imageType = cookie.getValue();
            if (imageType.equals(SessionAttributes.IMAGE_TYPE_TRAJECTORY)) {
                return true;
            } else if (imageType.equals(SessionAttributes.IMAGE_TYPE_PROFILE)) {
                return false;
            } else if (imageType.equals(SessionAttributes.IMAGE_TYPE_HYBRID)) {
                return false;
            } else {
                return true;
            }
        } else {
            // Not set - default value
            return true;
        }
    }

    public static boolean isProfileVisualization(HttpServletRequest request) {
        String cookieKey = SessionAttributes.IMAGE;
        Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
        if (cookie != null) {
            String imageType = cookie.getValue();
            if (imageType.equals(SessionAttributes.IMAGE_TYPE_TRAJECTORY)) {
                return false;
            } else if (imageType.equals(SessionAttributes.IMAGE_TYPE_PROFILE)) {
                return true;
            } else if (imageType.equals(SessionAttributes.IMAGE_TYPE_HYBRID)) {
                return false;
            } else {
                return false;
            }
        } else {
            // Not set - default value
            return false;
        }
    }

    public static boolean isHybridVisualization(HttpServletRequest request) {
        String cookieKey = SessionAttributes.IMAGE;
        Cookie cookie = ServletUtil.getCookieFromKey(request, cookieKey);
        if (cookie != null) {
            String imageType = cookie.getValue();
            if (imageType.equals(SessionAttributes.IMAGE_TYPE_TRAJECTORY)) {
                return false;
            } else if (imageType.equals(SessionAttributes.IMAGE_TYPE_PROFILE)) {
                return false;
            } else if (imageType.equals(SessionAttributes.IMAGE_TYPE_HYBRID)) {
                return true;
            } else {
                return false;
            }
        } else {
            // Not set - default value
            return false;
        }
    }

    public static String getImageMapKey(HttpServletRequest request) {
        Cookie imKeyCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.IMAGE_MAP_KEY);
        if (imKeyCookie != null && !imKeyCookie.getValue().equals("")) {
            return imKeyCookie.getValue();
        }
        return null;
    }

    public static String getSessionID(HttpServletRequest request) {
        Cookie databaseCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.SESSION_IDENTIFIER);
        if (databaseCookie != null && !databaseCookie.getValue().equals("")) {
            return databaseCookie.getValue();
        }
        return null;
    }

    public static TrajectoryFile getActiveTrajectoryFile(HttpServletRequest request) {
        Cookie databaseCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.TRAJECTORY_FILE_NAME);
        if (databaseCookie != null && !databaseCookie.getValue().equals("")) {
            String fileName = databaseCookie.getValue();
            ExpressionDataSet eDB = getActivePrimaryExpressionDatabase(request);
            if (eDB != null) {
                return TrajectoryFileFactory.getUniqueInstance().getFile(eDB.getID(), fileName);
            }
        }
        return null;
    }

    public static ExpressionDataSet getActivePrimaryExpressionDatabase(HttpServletRequest request) {
        Cookie databaseCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.PRIMARY_EXPRESSION_DATABASE);
        if (databaseCookie != null && !databaseCookie.getValue().equals("")) {
            int dataSetID = 0;
            try {
                dataSetID = Integer.parseInt(databaseCookie.getValue());
            } catch (java.lang.NumberFormatException ex) {
                return null;
            }
            return ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, true);
        }
        return null;
    }

    public static ExpressionDataSet getActiveSecondaryExpressionDatabase(HttpServletRequest request) {
        Cookie databaseCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.SECONDARY_EXPRESSION_DATABASE);
        if (databaseCookie != null && !databaseCookie.getValue().equals("")) {
            int dataSetID = 0;
            try {
                dataSetID = Integer.parseInt(databaseCookie.getValue());
            } catch (java.lang.NumberFormatException ex) {
                return null;
            }
            return ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, true);
        }
        return null;
    }

    public static ExpressionDataSet getFocalExpressionDatabase(HttpServletRequest request) {
        Cookie databaseCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.FOCAL_DATABASE);
        if (databaseCookie != null && !databaseCookie.getValue().equals("")) {
            String focusType = databaseCookie.getValue();
            if (focusType.equals(SessionAttributes.SECONDARY)) {
                return getActiveSecondaryExpressionDatabase(request);
            } else {
                return getActivePrimaryExpressionDatabase(request);
            }
        }
        return null;
    }

    public static KnowledgeDataSet getActiveKnowledgeLibrary(HttpServletRequest request) {
        Cookie databaseCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.KNOWLEDGE_LIBRARY);
        if (databaseCookie != null && !databaseCookie.getValue().equals("")) {
            int dataSetID = 0;
            try {
                dataSetID = Integer.parseInt(databaseCookie.getValue());
            } catch (java.lang.NumberFormatException ex) {
                return null;
            }
            return KnowledgeDataSetFactory.getUniqueInstance().getDataSet(dataSetID, true);
        }
        return null;
    }

//    public static Label getDEGLabel(HttpServletRequest request) {
//        Cookie degLabelCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.DEG_COOKIE);
//        if (degLabelCookie != null && !degLabelCookie.getValue().equals("")) {
//            String databaseName = degLabelCookie.getValue();
//
//        }
//        return null;
//    }
    
}
