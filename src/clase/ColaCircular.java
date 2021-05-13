
public  class ColaCircular

{
	private static  final int TAM_COLA = 10;
	private int tam_cola;
	private  int inicio = 0;	//pos primer elemento si nelems>0
	private int nelems = 0;		//pos nuevo elemento: (inicio+nlems) %TAM_COLA
	private Object[] cola;
	
	ColaCircular(int tam)
	{
		tam_cola=tam;
		cola=new Object[tam_cola];
	}
	
	ColaCircular()
	{
		tam_cola=TAM_COLA;
		cola=new Object[tam_cola];
	}
	

	public synchronized void encolar (Object item) throws InterruptedException{
	
		System.out.println("Cola llena");
		 	while (nelems >=10) {
		 				 	wait();
		 		
		 		
		 	}
		 	cola[ (inicio+nelems++) %tam_cola] = item;
			
		
		
		notifyAll();
		
	}

 public synchronized Object desencolar() throws InterruptedException
	{
	 System.out.println("Cola vacia");
		while(nelems<=0) {
			
			wait();
			
		 	}
		
		Object o =  cola[inicio];
		inicio = ( inicio + 1) % tam_cola;
		nelems=nelems-1;

		notify();
				
		return o;
	}
	
	public static void main(String args[]){
			final int lista_numeros[]={234,543,123,123,445,123,123,765,234};
			ColaCircular c=new ColaCircular(10);
			
			for (int i=1; i<99; i++)
			{
				new acesor(c, i, lista_numeros).start();
			}
		}
}


class acesor extends Thread{
		int[] listanum;
		int internalVal;
		ColaCircular c;
		
		public acesor(ColaCircular n, int i, int[] listanum)
		{
			this.c=n;
			this.setName("Nuevo Hilo "+ i);
			this.listanum=listanum;
			internalVal=10000*i;
		}
		
		public void run()
		{
			
			for (int i=0; i<listanum.length;i++) {
				System.out.println("Leemos la posicion:  "+i);
				int nuevonum=listanum[i]+internalVal;
				Integer numero = new Integer(nuevonum);
				int milisegundos=listanum[i];
				
				System.out.println("Se está introduciendo el numero "+nuevonum+" en la posicion: "+ i );
				try {
					c.encolar(numero);
					sleep(milisegundos);
					c.desencolar();
				}catch(InterruptedException ex) {}
				
			}

			
		}
	}
	