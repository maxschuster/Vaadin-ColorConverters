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
import com.vaadin.shared.ui.colorpicker.Color;

/**
 * A {@link Converter} that can convert a {@link Color} to CSS hex color
 * notation and back
 * 
 * @author Max Schuster
 */
public class HexToColorConverter
        extends AbstractHexColorConverter<String, Color>
        implements ColorConverter.StringToColorConverter {

    private static final long serialVersionUID = 1L;

    public HexToColorConverter(String errorMessage) {
        super(errorMessage);
    }
    
}
