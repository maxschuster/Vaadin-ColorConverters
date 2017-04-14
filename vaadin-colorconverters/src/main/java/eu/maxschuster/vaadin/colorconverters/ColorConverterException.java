/*
 * Copyright 2017 mschuster.
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

/**
 * Exception thrown by {@link ColorConverter} implementations.
 * 
 * @author Max Schuster
 */
public class ColorConverterException extends Exception {

    public ColorConverterException() {
    }

    public ColorConverterException(String message) {
        super(message);
    }

    public ColorConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColorConverterException(Throwable cause) {
        super(cause);
    }
    
}
