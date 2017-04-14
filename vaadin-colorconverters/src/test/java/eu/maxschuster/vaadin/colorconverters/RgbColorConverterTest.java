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
public class RgbColorConverterTest extends
        AbstractConverterTest<RgbToColorConverter, ColorToRgbConverter> {

    public RgbColorConverterTest() {
        super(
                new RgbToColorConverter("Error converting rgb notation to color!"),
                new ColorToRgbConverter("Error converting color to rgb notation!")
        );
    }

    @Override
    public Color[] getColors() {
        return new Color[]{
            new Color(255, 255, 255),
            new Color(15, 224, 0),
            new Color(0, 0, 0)
        };
    }

    @Override
    public String[] getStrings() {
        return new String[]{
            "rgb(255,255,255)",
            "rgb(15,224,0)",
            "rgb(0,0,0)"
        };
    }

    @Override
    public String[] getInvalidStrings() {
        return new String[]{
            "asdf",
            "rgb(0)",
            "rgb(0,0)",
            "rgb(0,0,0,0)",
            "rgb(,,)",
            "rgb(,,,)",
            "rgb(256,0,0)"
        };
    }

}
