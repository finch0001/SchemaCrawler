import static sf.util.Utility.isBlank;
import static us.fatehi.commandlineparser.CommandLineUtility.applyApplicationLogLevel;
import static us.fatehi.commandlineparser.CommandLineUtility.logSystemClasspath;
import static us.fatehi.commandlineparser.CommandLineUtility.logSystemProperties;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.sql.DataSource;

import schemacrawler.schemacrawler.DatabaseConnectionOptions;
import schemacrawler.schemacrawler.DatabaseSpecificOverrideOptions;
import schemacrawler.schemacrawler.ExcludeAll;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.tools.executable.SchemaCrawlerExecutable;
import schemacrawler.tools.options.OutputOptions;
import schemacrawler.tools.options.TextOutputFormat;
import schemacrawler.utility.SchemaCrawlerUtility;

public final class ExecutableExample
{

  public static void main(final String[] args)
    throws Exception
  {
    // Turn application logging on by applying the correct log level
    applyApplicationLogLevel(Level.OFF);
    // Log system properties and classpath
    logSystemProperties();
    logSystemClasspath();

    // Create the options
    final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
    // Set what details are required in the schema - this affects the
    // time taken to crawl the schema
    options.setSchemaInfoLevel(SchemaInfoLevelBuilder.standard());
    options.setRoutineInclusionRule(new ExcludeAll());
    options
      .setSchemaInclusionRule(new RegularExpressionInclusionRule("PUBLIC.BOOKS"));

    final Path outputFile = getOutputFile(args);
    final OutputOptions outputOptions = new OutputOptions(TextOutputFormat.html,
                                                          outputFile);
    final String command = "schema";

    final SchemaCrawlerExecutable executable = new SchemaCrawlerExecutable(command);
    executable.setSchemaCrawlerOptions(options);
    executable.setOutputOptions(outputOptions);
    final Connection connection = getConnection();
    final DatabaseSpecificOverrideOptions databaseSpecificOverrideOptions = SchemaCrawlerUtility
      .matchDatabaseSpecificOverrideOptions(connection);
    executable.execute(connection, databaseSpecificOverrideOptions);

    System.out.println("Created output file, " + outputFile);
  }

  private static Connection getConnection()
    throws SchemaCrawlerException, SQLException
  {
    final String connectionUrl = "jdbc:hsqldb:hsql://localhost:9001/schemacrawler";
    final DataSource dataSource = new DatabaseConnectionOptions(connectionUrl);
    return dataSource.getConnection("sa", "");
  }

  private static Path getOutputFile(final String[] args)
  {
    final String outputfile;
    if (args != null && args.length > 0 && !isBlank(args[0]))
    {
      outputfile = args[0];
    }
    else
    {
      outputfile = "./schemacrawler_output.html";
    }
    final Path outputFile = Paths.get(outputfile).toAbsolutePath().normalize();
    return outputFile;
  }

}
