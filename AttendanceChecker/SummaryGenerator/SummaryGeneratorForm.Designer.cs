namespace SummaryGenerator
{
    partial class SummaryGeneratorForm
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
            this.fileCollection = new System.Windows.Forms.ListBox();
            this.fileAddButton = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.outFilePath = new System.Windows.Forms.TextBox();
            this.browseFile = new System.Windows.Forms.Button();
            this.startProcessing = new System.Windows.Forms.Button();
            this.exitButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(21, 23);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(113, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "出席確認情報ファイル";
            // 
            // fileCollection
            // 
            this.fileCollection.FormattingEnabled = true;
            this.fileCollection.Location = new System.Drawing.Point(24, 39);
            this.fileCollection.Name = "fileCollection";
            this.fileCollection.Size = new System.Drawing.Size(399, 95);
            this.fileCollection.TabIndex = 1;
            // 
            // fileAddButton
            // 
            this.fileAddButton.Location = new System.Drawing.Point(337, 140);
            this.fileAddButton.Name = "fileAddButton";
            this.fileAddButton.Size = new System.Drawing.Size(86, 23);
            this.fileAddButton.TabIndex = 2;
            this.fileAddButton.Text = "ファイル追加";
            this.fileAddButton.UseVisualStyleBackColor = true;
            this.fileAddButton.Click += new System.EventHandler(this.fileAddButton_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(21, 181);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(88, 13);
            this.label4.TabIndex = 9;
            this.label4.Text = "集計TXT出力先";
            // 
            // outFilePath
            // 
            this.outFilePath.Location = new System.Drawing.Point(24, 197);
            this.outFilePath.Name = "outFilePath";
            this.outFilePath.Size = new System.Drawing.Size(346, 20);
            this.outFilePath.TabIndex = 10;
            // 
            // browseFile
            // 
            this.browseFile.Location = new System.Drawing.Point(376, 195);
            this.browseFile.Name = "browseFile";
            this.browseFile.Size = new System.Drawing.Size(47, 23);
            this.browseFile.TabIndex = 11;
            this.browseFile.Text = "参照";
            this.browseFile.UseVisualStyleBackColor = true;
            this.browseFile.Click += new System.EventHandler(this.browseFile_Click);
            // 
            // startProcessing
            // 
            this.startProcessing.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.startProcessing.Location = new System.Drawing.Point(24, 244);
            this.startProcessing.Name = "startProcessing";
            this.startProcessing.Size = new System.Drawing.Size(111, 32);
            this.startProcessing.TabIndex = 12;
            this.startProcessing.Text = "集計処理実行";
            this.startProcessing.UseVisualStyleBackColor = true;
            this.startProcessing.Click += new System.EventHandler(this.startProcessing_Click);
            // 
            // exitButton
            // 
            this.exitButton.Location = new System.Drawing.Point(141, 244);
            this.exitButton.Name = "exitButton";
            this.exitButton.Size = new System.Drawing.Size(56, 31);
            this.exitButton.TabIndex = 13;
            this.exitButton.Text = "終了";
            this.exitButton.UseVisualStyleBackColor = true;
            this.exitButton.Click += new System.EventHandler(this.exitButton_Click);
            // 
            // SummaryGeneratorForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(443, 299);
            this.Controls.Add(this.exitButton);
            this.Controls.Add(this.startProcessing);
            this.Controls.Add(this.browseFile);
            this.Controls.Add(this.outFilePath);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.fileAddButton);
            this.Controls.Add(this.fileCollection);
            this.Controls.Add(this.label1);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "SummaryGeneratorForm";
            this.Text = "SummaryGenerator";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ListBox fileCollection;
        private System.Windows.Forms.Button fileAddButton;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox outFilePath;
        private System.Windows.Forms.Button browseFile;
        private System.Windows.Forms.Button startProcessing;
        private System.Windows.Forms.Button exitButton;
    }
}

