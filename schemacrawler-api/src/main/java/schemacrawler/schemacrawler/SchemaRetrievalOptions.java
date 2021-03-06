/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2020, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/
package schemacrawler.schemacrawler;


import static sf.util.Utility.isBlank;

import schemacrawler.plugin.EnumDataTypeHelper;
import schemacrawler.utility.TypeMap;
import sf.util.ObjectToString;

/**
 * Provides for database specific overrides for SchemaCrawler functionality.
 * This can add or inject database plugins, or override defaults. It is
 * recommended to build these options using factory methods in
 * SchemaCrawlerUtility.
 *
 * @author Sualeh Fatehi <sualeh@hotmail.com>
 */
public final class SchemaRetrievalOptions
  implements Options
{

  private final DatabaseServerType dbServerType;
  private final MetadataRetrievalStrategy fkRetrievalStrategy;
  private final MetadataRetrievalStrategy functionColumnRetrievalStrategy;
  private final MetadataRetrievalStrategy functionRetrievalStrategy;
  private final String identifierQuoteString;
  private final Identifiers identifiers;
  private final MetadataRetrievalStrategy indexRetrievalStrategy;
  private final InformationSchemaViews informationSchemaViews;
  private final MetadataRetrievalStrategy pkRetrievalStrategy;
  private final MetadataRetrievalStrategy procedureColumnRetrievalStrategy;
  private final MetadataRetrievalStrategy procedureRetrievalStrategy;
  private final boolean supportsCatalogs;
  private final boolean supportsSchemas;
  private final MetadataRetrievalStrategy tableColumnRetrievalStrategy;
  private final MetadataRetrievalStrategy tableRetrievalStrategy;
  private final TypeMap typeMap;
  private final EnumDataTypeHelper enumDataTypeHelper;

  protected SchemaRetrievalOptions(final SchemaRetrievalOptionsBuilder builder)
  {
    final SchemaRetrievalOptionsBuilder bldr =
      builder == null? SchemaRetrievalOptionsBuilder.builder(): builder;
    dbServerType = bldr.getDatabaseServerType();
    supportsSchemas = bldr.isSupportsSchemas();
    supportsCatalogs = bldr.isSupportsCatalogs();
    tableRetrievalStrategy = bldr.getTableRetrievalStrategy();
    tableColumnRetrievalStrategy = bldr.getTableColumnRetrievalStrategy();
    pkRetrievalStrategy = bldr.getPrimaryKeyRetrievalStrategy();
    indexRetrievalStrategy = bldr.getIndexRetrievalStrategy();
    fkRetrievalStrategy = bldr.getForeignKeyRetrievalStrategy();
    procedureRetrievalStrategy = bldr.getProcedureRetrievalStrategy();
    procedureColumnRetrievalStrategy =
      bldr.getProcedureColumnRetrievalStrategy();
    functionRetrievalStrategy = bldr.getFunctionRetrievalStrategy();
    functionColumnRetrievalStrategy = bldr.getFunctionColumnRetrievalStrategy();
    identifierQuoteString = bldr.getIdentifierQuoteString();
    informationSchemaViews = bldr.getInformationSchemaViews();
    identifiers = bldr.getIdentifiers();
    typeMap = bldr.getTypeMap();
    enumDataTypeHelper = bldr.getEnumDataTypeHelper();
  }

  public EnumDataTypeHelper getEnumDataTypeHelper()
  {
    return enumDataTypeHelper;
  }

  public DatabaseServerType getDatabaseServerType()
  {
    return dbServerType;
  }

  public MetadataRetrievalStrategy getForeignKeyRetrievalStrategy()
  {
    return fkRetrievalStrategy;
  }

  public MetadataRetrievalStrategy getFunctionColumnRetrievalStrategy()
  {
    return functionColumnRetrievalStrategy;
  }

  public MetadataRetrievalStrategy getFunctionRetrievalStrategy()
  {
    return functionRetrievalStrategy;
  }

  public String getIdentifierQuoteString()
  {
    if (!hasOverrideForIdentifierQuoteString())
    {
      return "";
    }
    return identifierQuoteString;
  }

  public Identifiers getIdentifiers()
  {
    return identifiers;
  }

  public MetadataRetrievalStrategy getIndexRetrievalStrategy()
  {
    return indexRetrievalStrategy;
  }

  public InformationSchemaViews getInformationSchemaViews()
  {
    return informationSchemaViews;
  }

  public MetadataRetrievalStrategy getPrimaryKeyRetrievalStrategy()
  {
    return pkRetrievalStrategy;
  }

  public MetadataRetrievalStrategy getProcedureColumnRetrievalStrategy()
  {
    return procedureColumnRetrievalStrategy;
  }

  public MetadataRetrievalStrategy getProcedureRetrievalStrategy()
  {
    return procedureRetrievalStrategy;
  }

  public MetadataRetrievalStrategy getTableColumnRetrievalStrategy()
  {
    return tableColumnRetrievalStrategy;
  }

  public MetadataRetrievalStrategy getTableRetrievalStrategy()
  {
    return tableRetrievalStrategy;
  }

  public TypeMap getTypeMap()
  {
    return typeMap;
  }

  public boolean hasOverrideForIdentifierQuoteString()
  {
    return !isBlank(identifierQuoteString);
  }

  public boolean hasOverrideForTypeMap()
  {
    return typeMap != null;
  }

  public boolean isSupportsCatalogs()
  {
    return supportsCatalogs;
  }

  public boolean isSupportsSchemas()
  {
    return supportsSchemas;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString()
  {
    return ObjectToString.toString(this);
  }

}
