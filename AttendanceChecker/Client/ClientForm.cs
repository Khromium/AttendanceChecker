using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace Client
{
    public partial class ClientForm : Form
    {
        private BinaryWriter w;
        private uint totalRegCount;

        public ClientForm()
        {
            InitializeComponent();
            this.totalRegCount = 0;
        }

        private void showSaveDialog()
        {
            SaveFileDialog sfd = new SaveFileDialog();
            sfd.FileName = "AttendanceCheckList.bin";
            sfd.Filter = "BINファイル(*.bin)|*.bin|すべてのファイル(*.*)|*.*";
            sfd.FilterIndex = 1;
            sfd.Title = "出席情報保存先のファイルを選択";
            sfd.RestoreDirectory = true;
            sfd.OverwritePrompt = true;
            sfd.CheckPathExists = true;

            //ダイアログを表示する
            if (sfd.ShowDialog() == DialogResult.OK)
            {
                saveFilePath.Text = sfd.FileName;
                w = new BinaryWriter(File.OpenWrite(saveFilePath.Text));
            }
            else
                Application.Exit();
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            this.showSaveDialog();
            inputNumber.Focus();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {

        }

        private void enterButton_Click(object sender, EventArgs e)
        {
            
            if ((inputNumber.Text.Length != 5 && inputNumber.Text.Length != 10) || (System.Text.RegularExpressions.Regex.IsMatch(inputNumber.Text, "^[0-9]+$") == false))
            {
                messageLabel.Text = "入力された値が正しくありません．";
                return;
            }

            if (inputNumber.Text.Length == 10)
            inputNumber.Text = inputNumber.Text.Substring(5);

            if (long.Parse(inputNumber.Text) > 65535)
            {
                messageLabel.Text = "オーバーフロー．";
                return;
            }

            history.Items.Insert(0, inputNumber.Text);
            if (totalRegCount >= 6)
            {
                history.Items.RemoveAt(5);
            }



            //Write Student Number
            UInt16 studentNumber;
            studentNumber = UInt16.Parse(inputNumber.Text);
            w.Write(studentNumber);

         
                messageLabel.Text = "学籍番号 " + inputNumber.Text + " を登録しました．";
                totalRegisteredCount.Text = "登録件数： " + (++totalRegCount);
                inputNumber.Text = null;
            

        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (MessageBox.Show( "終了してもよろしいですか？", "確認",MessageBoxButtons.YesNo, MessageBoxIcon.Question
      ) == DialogResult.No)
            {
                e.Cancel = true;
            }
        }



        private void maxmin_Click(object sender, EventArgs e)
        {
            if (maxmin.Text == "最大化")
            {
                //フルスクリーンにする
                this.FormBorderStyle = FormBorderStyle.None;
                this.WindowState = FormWindowState.Maximized;
                maxmin.Text = "最小化";
            }
            else
            {
                //通常サイズに戻す
                this.FormBorderStyle = FormBorderStyle.Sizable;
                this.WindowState = FormWindowState.Normal;
                maxmin.Text = "最大化";
            }
            inputNumber.Focus();
        }

        private void openinpath_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process.Start(saveFilePath.Text.Remove(saveFilePath.Text.LastIndexOf("\\")));
            inputNumber.Focus();
        }
    }
}
