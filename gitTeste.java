// Teste com o repositorio danielGit do GitHub com o repositorio local danielGitRepository na minha maquina.
// Daniel Saes

package teste;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TesteDatas {

	public static void main(String[] args) {

//		Instant i1 = Instant.EPOCH;
//		Instant i2 = Instant.now();
//		
//		long s = Duration.between(i1, i2).getSeconds();
//		System.out.println(s);
//
//		System.out.println(i1);
//		System.out.println(i2);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate d1 = LocalDate.of(2006, 5, 26);
		//LocalDate d1 = LocalDate.of(1983, 10, 30);
		System.out.println(formatter.format(d1));
		
		LocalDate d = LocalDate.parse("13/11/2011", formatter);
		System.out.println(d.format(formatter));
		
		

		
		
		LocalDate agora = LocalDate.now();
		System.out.println(formatter.format(agora));
		
		System.out.println(ChronoUnit.YEARS.between(d1, agora));
		System.out.println(ChronoUnit.MONTHS.between(d1, agora));
		System.out.println(ChronoUnit.DAYS.between(d1, agora));
	
		System.out.println(Period.between(d1, agora));
		
		
		Period tempoDeVidaDaniel = Period.between(d1, agora);
		
		
		System.out.println(tempoDeVidaDaniel.getYears());
		System.out.println(tempoDeVidaDaniel.getMonths());
		System.out.println(tempoDeVidaDaniel.getDays());
		
		
		LocalDate davi = LocalDate.of(2019, 6, 20);
		System.out.println(formatter.format(davi));
		System.out.println(davi.format(formatter));
		Period tempoDeVidaDavi = Period.between(davi, agora);
		
		System.out.println(tempoDeVidaDavi.getYears());
		System.out.println(tempoDeVidaDavi.getMonths());
		System.out.println(tempoDeVidaDavi.getDays());
		
		
		

	}

}
