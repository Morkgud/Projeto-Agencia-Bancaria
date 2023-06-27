package programa.utilitario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {
//	public static final NumberFormat FORMATANDO_VALORES = new DecimalFormat("R$ #,##0.00");

	public static String formatarValorMonetario(BigDecimal valor) {
		NumberFormat formatador = new DecimalFormat("R$ #,##0.00");
		return formatador.format(valor.setScale(2, RoundingMode.HALF_EVEN));
	}

}
