package common;

import java.util.List;

/**
 * we need this interface to simplify testing. @see{BerechnerTest}
 */
public interface MonatsdatenBerechner
{

	public abstract void fillData(List<Monatsdaten> monate);

}
