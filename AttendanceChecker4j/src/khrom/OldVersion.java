package khrom;

import javafx.stage.FileChooser;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 昔の形式のファイルの読み書き
 */
public class OldVersion {
    /**
     * 点呼ソフトver1,ver2で出力されるバイナリファイルの読み込み場所
     *
     * @param inFile 入力ファイル
     * @return 読み取りデータ
     * @throws IOException
     */
    public static List<String> read(File inFile) throws IOException {
        List<String> resultList = new ArrayList<>();
        DataInputStream dataInStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inFile.getPath())));
        byte buf[] = new byte[2];
        byte[] swap = new byte[4];
        while (dataInStream.read(buf) != -1) {
            swap[0] = 0;//業が深い
            swap[1] = 0;
            swap[2] = buf[1];
            swap[3] = buf[0];
            resultList.add(String.valueOf(ByteBuffer.wrap(swap).getInt()));
        }
        dataInStream.close();
        return resultList;
    }

    /**
     * 点呼ソフトver1,ver2で出力されるバイナリファイル形式の書き出し
     * もし学籍番号に 65535以上の数字が入っていた場合は仕様上書き込めないので注意！！
     *
     * @param outFile 出力ファイル
     * @param numbers 学籍番号データ
     * @return
     * @throws IOException
     */
    public static void write(File outFile, List<String> numbers) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outFile.getPath())));
        byte buf[] = new byte[2];
        for (String number : numbers) {
            byte[] numberBytes = ByteBuffer.allocate(4).putInt(Integer.parseInt(number)).array();
            assert Integer.parseInt(number) <= 65535;//16ビットの最大値
            if (Integer.parseInt(number) > 65535) {
                System.out.println("overflow");
                continue;
            }
            buf[1] = numberBytes[2];
            buf[0] = numberBytes[3];
            dataOutputStream.write(buf);
        }
        dataOutputStream.flush();
        dataOutputStream.close();
    }
}
