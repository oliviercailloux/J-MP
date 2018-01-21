package io.github.oliviercailloux.jlp.elements;

/**
 * A boolean operator that can be used to relate the left hand side and the
 * right hand side in a constraint.
 *
 * @author Olivier Cailloux
 *
 */
public enum ComparisonOperator {

	/**
	 * Equal
	 */
	EQ,
	/**
	 * Greater or equal
	 */
	GE,
	/**
	 * Less or equal
	 */
	LE;

	public String toAsciiString() {
		switch (this) {
		case LE:
			return "<=";
		case GE:
			return ">=";
		case EQ:
			return "=";
		default:
			throw new IllegalStateException("Unknown operator.");
		}
	}

	@Override
	public String toString() {
		switch (this) {
		case LE:
			return "≤";
		case GE:
			return "≥";
		case EQ:
			return "=";
		default:
			throw new IllegalStateException("Unknown operator.");
		}
	}

}
