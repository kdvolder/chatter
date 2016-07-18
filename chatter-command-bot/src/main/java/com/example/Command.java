package com.example;

/**
 * When a message that looks like a command is received, it is parsed and the command info stored in 
 * a {@link Command} object.
 * 
 * @author Kris De Volder
 */
public class Command {
	
	/**
	 * The full text of the original message, not parsed.
	 */
	private final String msg;
	
	/**
	 * Name of the command (e.g 'echo' or 'date')
	 */
	private final String name;

	/**
	 * The remainder of the message after stripping of the command trigger (which contains the command name)
	 */
	private final String argument;

	public Command(String msg, String name, String argument) {
		super();
		this.msg = msg;
		this.name = name;
		this.argument = argument;
	}

	public String getMsg() {
		return msg;
	}

	public String getName() {
		return name;
	}

	public String getArgument() {
		return argument;
	}

	@Override
	public String toString() {
		return "Command [name=" + name + ", argument=" + argument + "]";
	}
	
	
}
