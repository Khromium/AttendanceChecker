package khrom;

import java.util.Calendar;

/**
 * Created by khrom on 2016/12/26.
 */
public class DateUtils {
    /**
     * デフォルトのファイル名を取ってくる。
     * 命名規則は普段の点呼の再現
     *
     * @return
     */
    public static String getDefaultFileName() {
        String amPM;
        amPM = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
        amPM += Calendar.getInstance().get(Calendar.AM_PM) == Calendar.PM ? "pm" : "am";
        return amPM;
    }
}
