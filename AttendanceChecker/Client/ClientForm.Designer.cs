namespace Client
{
    partial class ClientForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.saveFilePath = new System.Windows.Forms.TextBox();
            this.inputNumber = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.enterButton = new System.Windows.Forms.Button();
            this.messageLabel = new System.Windows.Forms.Label();
            this.totalRegisteredCount = new System.Windows.Forms.Label();
            this.maxmin = new System.Windows.Forms.Button();
            this.history = new System.Windows.Forms.ListBox();
            this.historytitle = new System.Windows.Forms.Label();
            this.openinpath = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("MS UI Gothic", 10F);
            this.label1.Location = new System.Drawing.Point(46, 37);
            this.label1.Margin = new System.Windows.Forms.Padding(7, 0, 7, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(93, 27);
            this.label1.TabIndex = 0;
            this.label1.Text = "保存先";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // saveFilePath
            // 
            this.saveFilePath.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.saveFilePath.Enabled = false;
            this.saveFilePath.Location = new System.Drawing.Point(52, 66);
            this.saveFilePath.Margin = new System.Windows.Forms.Padding(7, 6, 7, 6);
            this.saveFilePath.Name = "saveFilePath";
            this.saveFilePath.ReadOnly = true;
            this.saveFilePath.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.saveFilePath.Size = new System.Drawing.Size(548, 31);
            this.saveFilePath.TabIndex = 3;
            this.saveFilePath.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            // 
            // inputNumber
            // 
            this.inputNumber.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.inputNumber.Font = new System.Drawing.Font("Consolas", 21.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.inputNumber.ImeMode = System.Windows.Forms.ImeMode.Disable;
            this.inputNumber.Location = new System.Drawing.Point(52, 177);
            this.inputNumber.Margin = new System.Windows.Forms.Padding(7, 6, 7, 6);
            this.inputNumber.Name = "inputNumber";
            this.inputNumber.Size = new System.Drawing.Size(548, 75);
            this.inputNumber.TabIndex = 1;
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("MS UI Gothic", 10F);
            this.label2.Location = new System.Drawing.Point(46, 147);
            this.label2.Margin = new System.Windows.Forms.Padding(7, 0, 7, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(310, 27);
            this.label2.TabIndex = 4;
            this.label2.Text = "バーコード入力 / 学籍番号";
            // 
            // enterButton
            // 
            this.enterButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.enterButton.Font = new System.Drawing.Font("MS UI Gothic", 10F);
            this.enterButton.Location = new System.Drawing.Point(628, 177);
            this.enterButton.Margin = new System.Windows.Forms.Padding(7, 6, 7, 6);
            this.enterButton.Name = "enterButton";
            this.enterButton.Size = new System.Drawing.Size(108, 72);
            this.enterButton.TabIndex = 2;
            this.enterButton.Text = "登録";
            this.enterButton.UseVisualStyleBackColor = true;
            this.enterButton.Click += new System.EventHandler(this.enterButton_Click);
            // 
            // messageLabel
            // 
            this.messageLabel.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.messageLabel.AutoSize = true;
            this.messageLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.messageLabel.Location = new System.Drawing.Point(299, 298);
            this.messageLabel.Margin = new System.Windows.Forms.Padding(7, 0, 7, 0);
            this.messageLabel.Name = "messageLabel";
            this.messageLabel.Size = new System.Drawing.Size(171, 37);
            this.messageLabel.TabIndex = 6;
            this.messageLabel.Text = "入力待ち...";
            // 
            // totalRegisteredCount
            // 
            this.totalRegisteredCount.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.totalRegisteredCount.AutoSize = true;
            this.totalRegisteredCount.Location = new System.Drawing.Point(299, 345);
            this.totalRegisteredCount.Margin = new System.Windows.Forms.Padding(7, 0, 7, 0);
            this.totalRegisteredCount.Name = "totalRegisteredCount";
            this.totalRegisteredCount.Size = new System.Drawing.Size(137, 24);
            this.totalRegisteredCount.TabIndex = 7;
            this.totalRegisteredCount.Text = "登録件数： 0";
            // 
            // maxmin
            // 
            this.maxmin.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.maxmin.Font = new System.Drawing.Font("MS UI Gothic", 10.125F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(128)));
            this.maxmin.Location = new System.Drawing.Point(629, 406);
            this.maxmin.Name = "maxmin";
            this.maxmin.Size = new System.Drawing.Size(107, 66);
            this.maxmin.TabIndex = 8;
            this.maxmin.Text = "最大化";
            this.maxmin.UseVisualStyleBackColor = true;
            this.maxmin.Click += new System.EventHandler(this.maxmin_Click);
            // 
            // history
            // 
            this.history.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.history.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.history.CausesValidation = false;
            this.history.Enabled = false;
            this.history.Font = new System.Drawing.Font("MS UI Gothic", 10F);
            this.history.ForeColor = System.Drawing.Color.Black;
            this.history.FormattingEnabled = true;
            this.history.ItemHeight = 27;
            this.history.Location = new System.Drawing.Point(51, 306);
            this.history.Name = "history";
            this.history.Size = new System.Drawing.Size(234, 166);
            this.history.TabIndex = 5;
            // 
            // historytitle
            // 
            this.historytitle.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.historytitle.AutoSize = true;
            this.historytitle.Font = new System.Drawing.Font("MS UI Gothic", 10F);
            this.historytitle.Location = new System.Drawing.Point(48, 271);
            this.historytitle.Margin = new System.Windows.Forms.Padding(7, 0, 7, 0);
            this.historytitle.Name = "historytitle";
            this.historytitle.Size = new System.Drawing.Size(66, 27);
            this.historytitle.TabIndex = 9;
            this.historytitle.Text = "履歴";
            // 
            // openinpath
            // 
            this.openinpath.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.openinpath.Font = new System.Drawing.Font("MS UI Gothic", 10.125F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(128)));
            this.openinpath.Location = new System.Drawing.Point(629, 66);
            this.openinpath.Name = "openinpath";
            this.openinpath.Size = new System.Drawing.Size(107, 42);
            this.openinpath.TabIndex = 10;
            this.openinpath.Text = "表示";
            this.openinpath.UseVisualStyleBackColor = true;
            this.openinpath.Click += new System.EventHandler(this.openinpath_Click);
            // 
            // ClientForm
            // 
            this.AcceptButton = this.enterButton;
            this.AutoScaleDimensions = new System.Drawing.SizeF(13F, 24F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(819, 532);
            this.Controls.Add(this.openinpath);
            this.Controls.Add(this.historytitle);
            this.Controls.Add(this.history);
            this.Controls.Add(this.maxmin);
            this.Controls.Add(this.totalRegisteredCount);
            this.Controls.Add(this.messageLabel);
            this.Controls.Add(this.enterButton);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.inputNumber);
            this.Controls.Add(this.saveFilePath);
            this.Controls.Add(this.label1);
            this.Margin = new System.Windows.Forms.Padding(7, 6, 7, 6);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.MinimumSize = new System.Drawing.Size(845, 552);
            this.Name = "ClientForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "AttendanceCheckerClient";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox saveFilePath;
        private System.Windows.Forms.TextBox inputNumber;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button enterButton;
        private System.Windows.Forms.Label messageLabel;
        private System.Windows.Forms.Label totalRegisteredCount;
        private System.Windows.Forms.Button maxmin;
        private System.Windows.Forms.ListBox history;
        private System.Windows.Forms.Label historytitle;
        private System.Windows.Forms.Button openinpath;


    }
}

