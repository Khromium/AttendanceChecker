using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.IO;

namespace SummaryGenerator
{
    public partial class SummaryGeneratorForm : Form
    {
        public SummaryGeneratorForm()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void fileAddButton_Click(object sender, EventArgs e)
        {
            //OpenFileDialogクラスのインスタンスを作成
            OpenFileDialog ofd = new OpenFileDialog();

            
            ofd.Filter =
                "BINファイル(*.bin)|*.bin|すべてのファイル(*.*)|*.*";
            //[ファイルの種類]ではじめに
            //「すべてのファイル」が選択されているようにする
            ofd.FilterIndex = 1;
            //タイトルを設定する
            ofd.Title = "開くファイルを選択してください";
            //ダイアログボックスを閉じる前に現在のディレクトリを復元するようにする
            ofd.RestoreDirectory = true;
            //存在しないファイルの名前が指定されたとき警告を表示する
            //デフォルトでTrueなので指定する必要はない
            ofd.CheckFileExists = true;
            //存在しないパスが指定されたとき警告を表示する
            //デフォルトでTrueなので指定する必要はない
            ofd.CheckPathExists = true;
            ofd.Multiselect = true;

            //ダイアログを表示する
            if (ofd.ShowDialog() == DialogResult.OK)
            {
                //OKボタンがクリックされたとき
                //選択されたファイル名をすべて表示する
                foreach (string fn in ofd.FileNames)
                {
                    fileCollection.Items.Add(fn);
                }
            }
        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void startProcessing_Click(object sender, EventArgs e)
        {
            ArrayList studentNumList = new ArrayList();

            foreach (string path in fileCollection.Items)
                using (BinaryReader reader = new BinaryReader(File.OpenRead(path)))
                    while(reader.BaseStream.Position < reader.BaseStream.Length)
                        studentNumList.Add(reader.ReadUInt16());

            //Sort
            studentNumList.Sort();
            
            //Write
            System.IO.StreamWriter sw = new System.IO.StreamWriter(
                outFilePath.Text,
                false,
                System.Text.Encoding.GetEncoding("shift_jis"));

            UInt32 counter = 0;
            UInt16 previousNum = 0;
            foreach (UInt16 data in studentNumList)
            {
                if (previousNum != data)
                {
                    sw.WriteLine(String.Format("{0:D5}", data));
                    previousNum = data;
                    ++counter;
                }
            }
            
            //閉じる
            sw.Close();

            MessageBox.Show("計" + counter + "件のレコードを出力しました．");
        }

        private void browseFile_Click(object sender, EventArgs e)
        {
            SaveFileDialog sfd = new SaveFileDialog();
            sfd.FileName = "AttendanceCheckList.txt";
            sfd.Filter = "textファイル(*.txt)|*.txt|すべてのファイル(*.*)|*.*";
            sfd.FilterIndex = 1;
            sfd.Title = "出席情報保存先のファイルを選択";
            sfd.RestoreDirectory = true;
            sfd.OverwritePrompt = true;
            sfd.CheckPathExists = true;

            //ダイアログを表示する
            if (sfd.ShowDialog() == DialogResult.OK)
                outFilePath.Text = sfd.FileName;
            
        }

        private void exitButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        

       
    }
}
