package freshman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//個体のクラス
public class TIndividual {

	private double fEvaluate;		//+無限大で実行不可能
	private TVector fvector;
	
	//コンストラクタ
	public TIndividual() {
		fEvaluate  = Double.NaN;
		fvector = new TVector();
	}
	
	public TIndividual(TIndividual src) {
		fEvaluate = src.fEvaluate;
		fvector = new TVector(src.fvector);
	}
	
	@Override
	public TIndividual clone() {
		return new TIndividual(this);
	}
	
	//TIndividualのコピー
	public TIndividual CopyFrom(TIndividual src) {
		fEvaluate = src.fEvaluate;
		fvector = src.fvector;
		return this;
	}
	
	public void WriteTo(PrintWriter pw) {
		pw.println(fEvaluate);
		fvector.WriteTo(pw);
	}
	
	public void ReadFrom(BufferedReader br)  throws IOException{
		fEvaluate = Double.parseDouble(br.readLine());
		fvector.ReadFrom(br);
	}
	
	@Override
	public String toString() {
		String str = fEvaluate + "\n";
		str += fvector.toString();
		return str;
	}
	
	public void setEvaluate(double eval) {
		fEvaluate = eval;
	}
	
	public double getEvaluate(TIndividual src) {
		return src.fEvaluate;
	}
	
	public void setVector(TVector v) {
		fvector = v;
	}
	
	public TVector getVector() {
		return fvector;
	}
	
	//メイン関数
	public static void main(String []args) {
		TIndividualTest();
	}
	
	
	//テストする関数
	public static void TIndividualTest() {
		TIndividual src = new TIndividual();
		//初期状態のsrc確認
		System.out.println("Test1");
		System.out.println(src);
		System.out.println(src.getVector());
		
		System.out.println("\n" + "Test2");
		TVector v1 = new TVector();
		v1.setDimension(3);
		
		//v1に値を代入
		for(int i=0; i< v1.getDimension(); ++i) {
			v1.setElement(i, i*5);
		}
		
		src.setEvaluate(1.0);
		src.setVector(v1);
		//set後のsrc確認
		System.out.println(src);
		System.out.println(src.getVector()); //なぜかベクトル要素が2行表示される・・・
		System.out.println(src.toString());  //こっちだとちゃんと1行で表示される
		
		System.out.println("\n" + "Test3");
		TIndividual src2 = new TIndividual();
		src2.CopyFrom(src);
		System.out.println("src:" + src + "\n" + "src2:" + src2);
		
		src2.setEvaluate(3.0);
		src2.setVector(src2.fvector.ScalorMul(2.0));
		System.out.println("src2 << Evaluate*3,Vector*2");
		System.out.println("src:" + src + "\n" + "src2:" + src2); //浅いコピーなのでsrc,src2のベクトルが2倍される
		
		//読み込み・書き出し
		try {
				
			File filename = new File("test_TIndicidual.txt"); 
			filename.createNewFile();
			

			//FileWriterオブジェクトの生成
			FileWriter fw = new FileWriter(filename);

			//BufferedWriterオブジェクトの生成
			BufferedWriter bw = new BufferedWriter(fw);

			//PrintWriterオブジェクトの生成
			PrintWriter pw = new PrintWriter(bw);
			src.WriteTo(pw);
			pw.close();
			
			//v2の内容をv1で書き換え
			BufferedReader br = new BufferedReader(new FileReader(filename)); 
			src2.ReadFrom(br); 
			br.close(); 
			System.out.println("\n" + "Test4");
			System.out.println("src: "+ src + "\n" + "src2 :" + src2);
			
		}catch(IOException e) { 
	 		System.out.println(e); 
		} 
		
		
		
	}
	
}
