package controler;

import javax.swing.ImageIcon;

import view.ControlPanel;

public class PuertaThread implements Runnable{

	private enum Estado{Cerrada,Abierta,Cerrandose};
	private static Estado EstadoActial;
	private static int Timer=0;
	private static ControlPanel Father;
	
	public PuertaThread(ControlPanel father) {
		Father=father;
	}
	
	@Override
	public void run() {
	setEstadoActial(Estado.Cerrada);
	while (true)
		{
		if (Timer!=0)
			{
			Timer--;
			if (Father!=null)Father.setImagen(PuertaAnimacion.Opening());
			try {
				Thread.sleep(Constantes.getGranularityTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.err.println("Waiting Time for door error");
			}
			if (Father!=null) Father.setWaitingTime(Integer.toString(Timer));
			}
		else {
			if (EstadoActial==Estado.Abierta)
			{
				EstadoActial=Estado.Cerrandose;
				if (Father!=null)Father.setImagen(PuertaAnimacion.Closing());
				try {
					Thread.sleep(Constantes.getGranularityTime());;
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.err.println("Waiting Time for door error");
				}
			}
			else 
				if (EstadoActial==Estado.Cerrandose)
				{
					if (PuertaAnimacion.final_())
					EstadoActial=Estado.Cerrada;
					if (Father!=null)Father.setImagen(PuertaAnimacion.Closing());
					try {
						Thread.sleep(Constantes.getGranularityTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.err.println("Waiting Time for door error");
					}
				}else
			{
				EstadoActial=Estado.Cerrada;
				try {
					Thread.sleep(Constantes.getGranularityTime());;
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.err.println("Waiting Time for door error");
				}
			}
			}
		}
		
	}

	public synchronized static Estado getEstadoActial() {
		return EstadoActial;
	}
	
	public synchronized static void setEstadoActial(Estado estadoActial) {
		EstadoActial = estadoActial;
	}
	
	public static void setTimer(int timer) {
		Timer = timer;
	}
	
	public static int getTimer() {
		return Timer;
	}

	public static void OpenMandatori() {
		if (getEstadoActial()!=Estado.Abierta)
			{
			setEstadoActial(Estado.Abierta);
			if (Father!=null)
				Father.setImagen(PuertaAnimacion.Opening());
			}
		
	}
	
	public static boolean isOpen()
	{
		return (getEstadoActial()==Estado.Abierta);
	}
	
}
