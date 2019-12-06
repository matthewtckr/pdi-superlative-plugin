package org.pentaho.di.trans.steps.superlative;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.trans.steps.loadsave.LoadSaveTester;
import org.pentaho.di.trans.steps.loadsave.validator.FieldLoadSaveValidator;
import org.pentaho.di.trans.steps.loadsave.validator.FieldLoadSaveValidatorFactory;

public class SuperlativeFilterMetaTest {
  @Before
  public void setUp() throws Exception {
    KettleEnvironment.init();
    PluginRegistry.init( false );
  }

  @Test
  public void testLoadSave() throws Exception {
    List<String> attributes = Arrays.asList( "valueFieldName", "numRowsToSave", "filterType" );
    HashMap<String, String> emptyMap = new HashMap<String, String>();

    LoadSaveTester runner = new LoadSaveTester( SuperlativeFilterMeta.class, attributes, emptyMap, emptyMap );

    FieldLoadSaveValidatorFactory factory = runner.getFieldLoadSaveValidatorFactory();
    factory.registerValidator( factory.getName( FilterType.class ), new FilterTypeFieldLoadSaveValidator() );

    runner.testXmlRoundTrip();
    runner.testRepoRoundTrip();
  }

  public class FilterTypeFieldLoadSaveValidator implements FieldLoadSaveValidator<FilterType> {
    public FilterType getTestObject() {
      FilterType[] values = FilterType.values();
      return values[(new Random().nextInt( values.length ))];
    }
    public boolean validateTestObject( FilterType testObject, Object actual ) {
      return testObject == actual;
    }
  }
}
