package freshman;

public class TRex {

	
	private TVector fvector;
	private TRandom frandaom;
	
	
	/**
	 * コンストラクタ
	 */
	public TRex(){
		//TRandom frandom = new TRandom();
		fvector = new TVector();

	}
	

	
	public TVector creatRex(TPopulation fpop, int dimension, int k) {
		
		if(fvector.getDimension() != fpop.getIndividual(0).getVector().getDimension()) {
			fvector.setDimension(fpop.getIndividual(0).getVector().getDimension());
		}
		
		TRandom frandom = new TRandom();
		
		//平均vxg
		TVector vxg = new TVector();
		TVector vran = new TVector();
		TVector vtemp = new TVector();

		vxg.setDimension(fpop.getIndividual(0).getVector().getDimension());
		vran.setDimension(fpop.getIndividual(0).getVector().getDimension());
		vtemp.setDimension(fpop.getIndividual(0).getVector().getDimension());
		for(int i=0; i < dimension+k ; ++i) {
			vxg.add(fpop.getIndividual(i).getVector());
		}
		vxg.scalarMul(1.0 / (dimension+k));
		
		//分散siguma
		double siguma = 0.0;
		siguma = (double) 1.0 / (dimension + k);

		for(int i=0; i < fpop.getIndividual(0).getVector().getDimension(); ++i) {
			fvector.setElement(i, 0.0);
		}
		
		for(int i=0; i < dimension+k; ++i) {
			vran.CopyFrom(frandom.TCreatRandomSeiki(0.0, siguma, dimension).fvector);
			vtemp.add(fpop.getIndividual(i).getVector());
			vtemp.subtract(vxg);
			vtemp.calMul(vran);
			fvector.add(vtemp);
		}
		fvector.add(vxg);
		
		
		return fvector;
	}
	
	
	
	
	
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		TRexTest();
	}

	
	public static void TRexTest() {
		
		double min= -5.0;
		double max= 5.0;
		int dimension = 5;//20; 
		int popSize= 10;//14*dimension;
		int np = 2;
		int k = 2;

		

		TJGG jgg1 = new TJGG();
		TRex rex1 = new TRex();
		TRandom ran1 = new TRandom();
		jgg1.setRandom(ran1);
		TVector v1 = new TVector();
		TVector v2 = new TVector();



		//まず初期集団作成
		jgg1.TInitializePop(min, max, popSize, dimension);
		
		TPopulation tpop = new TPopulation();
		tpop = jgg1.getPopulation();
		System.out.println(tpop);
		
		//REXで子を生成
		v1 = rex1.creatRex(tpop, dimension, k);
		System.out.println("v1:" + v1);
		v2 = rex1.creatRex(tpop, dimension, k);
		System.out.println("v2:" + v2);
	}
	
}
