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

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.shared.ui.colorpicker.Color;

/**
 * Base interface for {@link Converter}s that convert to or from {@link Color}
 * 
 * @author Max Schuster
 * @param <PRESENTATION> The presentation type.
 * @param <MODEL> The model type.
 */
public interface ColorConverter<PRESENTATION, MODEL> extends Converter<PRESENTATION, MODEL> {
    
    String getErrorMessage();
    
    /**
     * Serializes the given {@link Color} as a {@link String}. The String must
     * be unserializeable by {@link #unserializeColor(java.lang.String)}
     *
     * @param color The {@link Color} to serialize. Never {@code null}.
     * @return {@link Color} serialized as {@link String}
     * @throws ColorConverterException If the {@link Color} can't be serialized
     */
    String serializeColor(Color color) throws ColorConverterException;

    /**
     * Unserializes the given {@link String} as a {@link Color}.
     *
     * @param string The {@link String} to unserialize. Never {@code null}.
     * @return {@link String} unserialized as {@link Color}
     * @throws ColorConverterException If the {@link String} can't be unserialized
     */
    Color unserializeColor(String string) throws ColorConverterException;
    
    public interface StringToColorConverter extends ColorConverter<String, Color> {

        @Override
        default Result<Color> convertToModel(String value, ValueContext context) {
            if (value == null) {
                return Result.ok(null);
            }
            
            value = value.trim();
            
            try {
                return Result.ok(unserializeColor(value));
            } catch (ColorConverterException ex) {
                return Result.error(getErrorMessage());
            }
        }

        @Override
        default String convertToPresentation(Color value, ValueContext context) {
            if (value == null) {
                return null;
            }
            try {
                return serializeColor(value);
            } catch (ColorConverterException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        
    }
    
    public interface ColorToStringConverter extends ColorConverter<Color, String> {

        @Override
        default Result<String> convertToModel(Color value, ValueContext context) {
            if (value == null) {
                return Result.ok(null);
            }
            try {
                return Result.ok(serializeColor(value));
            } catch (ColorConverterException ex) {
                return Result.error(getErrorMessage());
            }
        }

        @Override
        default Color convertToPresentation(String value, ValueContext context) {
            if (value == null) {
                return null;
            }
            
            value = value.trim();
            
            try {
                return unserializeColor(value);
            } catch (ColorConverterException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        
    }
    
}
