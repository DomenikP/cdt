package org.eclipse.cdt.core;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */
 
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Properties;

import org.eclipse.cdt.internal.core.ProcessClosure;
import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.cdt.utils.spawner.ProcessFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;


public class CommandLauncher {
	
	public final static int COMMAND_CANCELED= 1;
	public final static int ILLEGAL_COMMAND= -1;
	public final static int OK= 0;	
		
	protected Process fProcess;
	protected boolean fShowCommand;
	protected String[] fCommandArgs;
	
	protected String fErrorMessage;
	
	/**
	 * The number of milliseconds to pause
	 * between polling.
	 */
	protected static final long DELAY = 50L;	
		
	/**
	 * Creates a new launcher
	 * Fills in stderr and stdout output to the given streams.
	 * Streams can be set to <code>null</code>, if output not required
	 */
	public CommandLauncher() {
		fProcess= null;
		fShowCommand= false;
	}
	
	/**
	 * Sets if the command should be printed out first before executing
	 */
	public void showCommand(boolean show) {
		fShowCommand= show;
	}
	
	public String getErrorMessage() {
		return fErrorMessage;
	}
	
	public String[] getCommandArgs() {
		return fCommandArgs;
	}	

	public Properties getEnvironment() {
		return EnvironmentReader.getEnvVars();
	}

	/**
	 * Constructs a command array that will be passed to the process
	 */
	protected String[] constructCommandArray(String command, String[] commandArgs) {
		String[] args = new String[1 + commandArgs.length];
		args[0] = command;
		System.arraycopy(commandArgs, 0, args, 1, commandArgs.length);
		return args;
	}

	/**
	 * Execute a command
	 */
	public Process execute(IPath commandPath, String[] args, String[] env, IPath changeToDirectory) {
		try {
			// add platform specific arguments (shell invocation) 
			fCommandArgs= constructCommandArray(commandPath.toOSString(), args);
			fProcess= ProcessFactory.getFactory().exec(fCommandArgs, env, changeToDirectory.toFile());
			fErrorMessage= "";
		} catch (IOException e) {
			fErrorMessage= e.getMessage();
			fProcess= null;
		}
		return fProcess;
	}
	
	/**
	 * Reads output form the process to the streams.
	 */
	public int waitAndRead(OutputStream out, OutputStream err) {
		if (fShowCommand) {
			printCommandLine(fCommandArgs, out);
		}	

		if (fProcess == null) {
			return ILLEGAL_COMMAND;
		}
				
		ProcessClosure closure= new ProcessClosure(fProcess, out, err);
		closure.runBlocking(); // a blocking call
		return OK;
	}
	
	/**
	 * Reads output form the process to the streams. A progress monitor is polled to
	 * test if the cancel button has been pressed.
	 * Destroys the process if the monitor becomes canceled
	 * override to implement a different way to read the process inputs
	 */
	public int waitAndRead(OutputStream out, OutputStream err, IProgressMonitor monitor) {
		if (fShowCommand) {
			printCommandLine(fCommandArgs, out);
		}	

		if (fProcess == null) {
			return ILLEGAL_COMMAND;
		}

		PipedOutputStream errOutPipe = new PipedOutputStream();
		PipedOutputStream outputPipe = new PipedOutputStream();
		PipedInputStream errInPipe, inputPipe;
		try {
			errInPipe = new PipedInputStream(errOutPipe);
			inputPipe = new PipedInputStream(outputPipe);
		} catch( IOException e ) {
			return COMMAND_CANCELED;
		}
						
		ProcessClosure closure= new ProcessClosure(fProcess, outputPipe, errOutPipe);
		closure.runNonBlocking();
		byte buffer[] = new byte[1024];
		int nbytes;
		while (!monitor.isCanceled() && closure.isAlive()) {
			try {
				if ( errInPipe.available() > 0 ) {
					nbytes = errInPipe.read(buffer);
					err.write(buffer, 0, nbytes);
					err.flush();
				}
				if ( inputPipe.available() > 0 ) {
					nbytes = inputPipe.read(buffer);
					out.write(buffer, 0, nbytes);
					out.flush();
				}
			} catch( IOException e) {
			}
			monitor.worked(0);
			try {
					Thread.sleep(DELAY);
			} catch (InterruptedException ie) {
			}
		}

		int state = OK;

		// Operation canceled by the user, terminate abnormally.
		if (monitor.isCanceled()) {
			closure.terminate();
			state = COMMAND_CANCELED;
		}

		try {
			fProcess.waitFor();
		} catch (InterruptedException e) {
			//System.err.println("Closure exception " +e);
			//e.printStackTrace();
		}
		
		return state;
	}	

	protected void printCommandLine(String[] commandArgs, OutputStream os) {
		if (os != null) {
			StringBuffer buf= new StringBuffer();
			for (int i= 0; i < commandArgs.length; i++) {
				buf.append(commandArgs[i]);
				buf.append(' ');
			}
			buf.append('\n');
			try {
				os.write(buf.toString().getBytes());
				os.flush();				
			} catch (IOException e) {
				// ignore;
			}
		}
	}
}
