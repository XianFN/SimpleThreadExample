
class GestorGaraje {
	private final int numPlazas;
	private int numCoches = 0;
	private boolean prioridadE = true; // Prioridad para entrada de vehículos por el Este
	private int esperandoE = 0;        // Número de coches esperando en la entrada Este
	private int esperandoW = 0;		   // Número de coches esperando en la entrada Oeste	

	GestorGaraje(int numPlazas) {
		this.numPlazas = numPlazas;
	}	
	 public synchronized void entraCochePorEste()  throws InterruptedException{
	 esperandoE++;
	  if(puedoEntrar(prioridadE, esperandoW)) {
	   System.out.println("Se puede entrar por el este");
	  }else {
	   wait();
	   System.out.println("No se puede entrar por el este");
	  }

	  esperandoE--;   
	 }
	 public synchronized void entraCochePorOeste()  throws InterruptedException{
	  esperandoW++;
	  if(puedoEntrar(!prioridadE, esperandoE)) {
	   System.out.println("Se puede entrar por el oeste");
	  }else {
	   wait();
	   System.out.println("No se puede entrar por el oeste");
	  }  
	
	  
	  esperandoW--;
	 }
	 private boolean puedoEntrar(boolean miPrioridad, int esperandoEnLaOtra) {//True si puede entrar, false si no
	  if (numCoches >= numPlazas)
	   return false;
	  if (miPrioridad)
	   return true;
	  return esperandoEnLaOtra == 0;
	 }
	 public synchronized void saleCoche() {
	
	  notifyAll();
  
	 } 

	
	public static void main(String args[]) {
		GestorGaraje garaje = new GestorGaraje(5);
		for(int i=0;i<10;i++) {
			(new Thread(new Vehiculo(i, garaje))).start();
		}
	}
}

class Vehiculo implements Runnable{
	private int matricula;
	GestorGaraje garaje;
	
	public Vehiculo(int matricula, GestorGaraje garaje) {
		this.matricula = matricula;		
		this.garaje = garaje;
	}
	
	public void run() {
	
		if (matricula%2==0) {
			try {
			garaje.entraCochePorEste(); 
			System.out.println("Entra el vehiulo con matricula: "+matricula);
			Thread.sleep(matricula);
		
			garaje.saleCoche();
			System.out.println("Sale el coche con matricula: "+matricula);
			}catch(InterruptedException ex) {}
			
		}else
			try {
			garaje.entraCochePorOeste();
			System.out.println("Entra el vehiulo con matricula: "+matricula);
			Thread.sleep(matricula);
			
			garaje.saleCoche();
			System.out.println("Sale el coche con matricula: "+matricula);
			}catch(InterruptedException ex) {}

			
	}

}