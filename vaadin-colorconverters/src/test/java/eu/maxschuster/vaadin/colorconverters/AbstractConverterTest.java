/*
 * Copyright 2017 Max Schuster.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.maxschuster.vaadin.colorconverters;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.shared.ui.colorpicker.Color;
import java.util.Locale;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author Max Schuster
 * @param <STC> The {@link String} to {@link Color} converter
 * @param <CTS> The {@link Color} to {@link String} converter
 */
public abstract class AbstractConverterTest<STC extends ColorConverter<String, Color>, CTS extends ColorConverter<Color, String>> {
    
    protected static final Locale LOCALE = Locale.GERMANY;

    private final STC stringToColorConverter;
    
    private final CTS colorToStringConverter;
    
    private final ValueContext valueContext;

    public AbstractConverterTest(STC stringToColorConverter,
            CTS colorToStringConverter) {
        this.stringToColorConverter = stringToColorConverter;
        this.colorToStringConverter = colorToStringConverter;
        this.valueContext = new ValueContext(LOCALE);
    }

    public STC getStringToColorConverter() {
        return stringToColorConverter;
    }
    
    public CTS getColorToStringConverter() {
        return colorToStringConverter;
    }

    abstract public Color[] getColors();

    abstract public String[] getStrings();
    
    abstract public String[] getInvalidStrings();

    @Test
    public void convertModelToPresentation() {
        String[] strings = getStrings();
        Color[] colors = getColors();
        
        Assert.assertEquals(colors.length, strings.length);
        
        int size = strings.length;
        
        STC stc = getStringToColorConverter();
        CTS cts = getColorToStringConverter();
        
        for (int i = 0; i < size; i++) {
            String string = strings[i];
            Color color = colors[i];
            
            String convertedString =
                    stc.convertToPresentation(color, valueContext);
            Assert.assertEquals("Error converting model '" + color.getCSS()
                    + "' to presentation '" + string + "'",
                    convertedString.toLowerCase(Locale.GERMANY),
                    string.toLowerCase(Locale.GERMANY));
            
            Color convertedColor = 
                    cts.convertToPresentation(string, valueContext);
            Assert.assertEquals("Error converting model '" + string
                    + "' to presentation '" + color.getCSS() + "'",
                    convertedColor, color);
        }
    }

    @Test
    public void convertPresentationToModel() {
        String[] strings = getStrings();
        Color[] colors = getColors();
        
        Assert.assertEquals(colors.length, strings.length);
        
        int size = colors.length;
        
        STC stc = getStringToColorConverter();
        CTS cts = getColorToStringConverter();
        
        for (int i = 0; i < size; i++) {
            String string = strings[i];
            Color color = colors[i];
            
            Result<Color> colorResult =
                    stc.convertToModel(string, valueContext);
            colorResult.ifError(message -> {
                Assert.fail(message);
            });
            colorResult.ifOk(convertedColor -> {
                Assert.assertEquals("Error converting presentation '" + string
                        + "' to model '" + color.getCSS() + "'", convertedColor, color);
            });
            
            
            Result<String> stringResult =
                    cts.convertToModel(color, valueContext);
            
            stringResult.ifError(message -> {
                Assert.fail(message);
            });
            
            stringResult.ifOk(convertedString -> {
                Assert.assertEquals("Error converting presentation '" + color.getCSS()
                        + "' to model '" + string + "'",
                        convertedString.toLowerCase(LOCALE),
                        string.toLowerCase(LOCALE));
            });
            
        }
        
    }

    @Test
    public void convertNullModelToPresentationShouldReturnNull() {
        STC stc = getStringToColorConverter();
        Assert.assertNull(stc.convertToPresentation(null, valueContext));
        
        CTS cts = getColorToStringConverter();
        Assert.assertNull(cts.convertToPresentation(null, valueContext));
    }

    @Test
    public void convertNullPresentationToModelShouldReturnNull() {
        STC stc = getStringToColorConverter();
        Result<Color> resultColor = stc.convertToModel(null, valueContext);
        resultColor.ifError(message -> {
            Assert.fail(message);
        });
        resultColor.ifOk(color -> {
            Assert.assertNull(color);
        });
        
        CTS cts = getColorToStringConverter();
        Result<String> stringResult = cts.convertToModel(null, valueContext);
        stringResult.ifError(message -> {
            Assert.fail(message);
        });
        stringResult.ifOk(string -> {
            Assert.assertNull(string);
        });
    }
    
    @Test
    public void convertInvalidStrings() {
        for (String invalidString : getInvalidStrings()) {
            Result<Color> result = stringToColorConverter
                    .convertToModel(invalidString, valueContext);
            result.ifOk(color -> {
                Assert.fail("Converter falsely accepted the string \""
                        + invalidString + "\" and converted it to a color!");
            });
        }
    }

}
