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

import com.vaadin.shared.ui.colorpicker.Color;

/**
 *
 * @author Max Schuster
 */
public class RgbaColorConverterTest extends
        AbstractConverterTest<RgbaToColorConverter, ColorToRgbaConverter> {

    public RgbaColorConverterTest() {
        super(
                new RgbaToColorConverter("Error converting rgba notation to color!"),
                new ColorToRgbaConverter("Error converting color to rgba notation!")
        );
    }

    @Override
    public Color[] getColors() {
        return new Color[]{
            new Color(255, 255, 255, 255),
            new Color(15, 224, 0, 153),
            new Color(0, 0, 0, 0)
        };
    }

    @Override
    public String[] getStrings() {
        return new String[]{
            "rgba(255,255,255,1)",
            "rgba(15,224,0,0.6)",
            "rgba(0,0,0,0)"
        };
    }

    @Override
    public String[] getInvalidStrings() {
        return new String[]{
            
        };
    }
    
    

}
