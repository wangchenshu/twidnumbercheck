package org.dxnetwork;

import java.util.HashMap;
import java.util.Scanner;

/**
 * 檢查台灣身份證號碼
 */
public class TwIdNumberCheck {
    // 戶籍碼
    private static final HashMap<String, Integer> HouseholdRegistrationCodeMap = new HashMap<>() {{
        put("A", 10); // 臺北市
        put("B", 11); // 臺中市
        put("C", 12); // 基隆市
        put("D", 13); // 臺南市
        put("E", 14); // 高雄市
        put("F", 15); // 新北市
        put("G", 16); // 宜蘭縣
        put("H", 17); // 桃園市
        put("I", 34); // 嘉義市
        put("J", 18); // 新竹縣
        put("K", 19); // 苗栗縣
        put("L", 20); // 臺中縣
        put("M", 21); // 南投縣
        put("N", 22); // 彰化縣
        put("O", 35); // 新竹市
        put("P", 23); // 雲林縣
        put("Q", 24); // 嘉義縣
        put("R", 25); // 臺南縣
        put("S", 26); // 高雄縣
        put("T", 27); // 屏東縣
        put("U", 28); // 花蓮縣
        put("V", 29); // 臺東縣
        put("W", 32); // 金門縣
        put("X", 30); // 澎湖縣
        put("Y", 31); // 陽明山管理局
        put("Z", 33); // 連江縣
    }};

    /**
     * 如何計算出正確身分證的檢查碼，以協助辨別身分證的真偽 ?
     * <p>
     * S=戶籍碼十位數×1+戶籍碼個位數×9+性別碼×8+N1×7+N2×6+N3×5+N4×4+N5×3+N6×2+N7×1
     * <p>
     * 若S除以10的餘數是R，則檢查碼=10-R。
     * <p>
     * <p>
     * <p>
     * 以國民身分證字號A12345678£為例，求檢查碼是多少 ?
     * <p>
     * A=10
     * <p>
     * S=1×1+0×9+1×8+2×7+3×6+4×5+5×4+6×3+7×2+8×1=121
     * <p>
     * 121除以10的餘數是1，
     * <p>
     * 檢查碼=10-1=9。
     *
     * @param no
     * @return
     */
    public static boolean isTaiwanIDNumber(String no) {
        if (no.length() != 10) {
            return false;
        }
        String[] digArr = new String[10];   // 儲存身分證號字元
        Scanner s = new Scanner(no).useDelimiter("");
        for (int i = 0; i < 10; i++) {
            digArr[i] = s.next();
        }

        int[] ID = new int[10];
        int householdRegistrationCodeNumber = HouseholdRegistrationCodeMap.getOrDefault(digArr[0], 0);
        // 驗證首位字母是否合法 驗證第一位是否為 1 or 2 (1=男生, 2=女生)
        if (householdRegistrationCodeNumber == 0 || (!digArr[1].equals("1") && !digArr[1].equals("2"))) {
            return false;
        }

        ID[0] = householdRegistrationCodeNumber / 10;
        ID[1] = householdRegistrationCodeNumber % 10;
        int lastNumber = Integer.parseInt(digArr[9]);

        // 取得檢查碥
        int check = getIdNumberCheck(ID, digArr);
        // 是否與第 10 數字相同
        return check == lastNumber;
    }

    /**
     * 取得身份證檢查碥
     *
     * @param ID
     * @param digArr
     * @return
     */
    private static int getIdNumberCheck(int[] ID, String[] digArr) {
        int sum = ID[0]; // 第 1 個數字乘以 1
        sum = sum + ID[1] * 9; //  第 2 個數字乘以 9
        int k = 8;
        for (int i = 1; i < 9; i++) { // 第 2 ~ 9 數字分別
            int value = Integer.parseInt(digArr[i]);
            sum = sum + value * k;   // 乘以 8 ~ 1 累加
            k--;
        }
        int check = 10 - (sum % 10); // 10 – 個位數
        if (check == 10) {
            return 0;
        }
        return check;
    }
}