/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2017 GwtMaterialDesign
 * %%
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
 * #L%
 */
package gwt.material.design.addins.client.webp;

import com.google.gwt.core.client.Scheduler;
import gwt.material.design.client.base.mixin.AttributeMixin;
import gwt.material.design.client.ui.MaterialImage;

//@formatter:off

/**
 * An extension to {@link MaterialImage} component which supports the latest
 * {@see <a href="https://developers.google.com/speed/webp/">WebP</a>} Image Format.
 * <p>
 * WebP lossless images are 26% smaller in size compared to PNGs. WebP lossy images are
 * 25-34% smaller than comparable JPEG images at equivalent SSIM quality index.
 * <p>
 * <pre>
 * {@code <ma:webp.MaterialWebpImage url="/images/my-image.webp" fallbackUrl="/images/my-image.png"/>
 *
 *     or
 *
 *    <ma:webp.MaterialWebpImage url="/images/my-image.webp" fallbackExtension="png"/>
 *     }
 * </pre>
 *
 * @author kevzlou7979
 */
//@formatter:on
public class MaterialWebpImage extends MaterialImage implements HasWebpFallback {

    private String fallbackUrl;
    private String fallbackExtension;
    private AttributeMixin<MaterialWebpImage> attributeMixin;

    @Override
    protected void onLoad() {
        super.onLoad();

        Scheduler.get().scheduleDeferred(() -> load());
    }

    /**
     * Will load and setup the webp fallback
     */
    protected void load() {
        // Check whether the fallback url is not null and will
        // be set the fallback attribute
        if (fallbackUrl != null) {
            setFallbackAttribute(fallbackUrl);
            return;
        } else {
            if (fallbackExtension != null) {
                fallbackUrl = getUrl();
                if (fallbackUrl.isEmpty()) {
                    return;
                }

                if (fallbackUrl.indexOf(".") > 0) {
                    fallbackUrl = fallbackUrl.substring(0, fallbackUrl.lastIndexOf(".")) + "." + fallbackExtension;
                    setFallbackAttribute(fallbackUrl);
                }
                return;
            }
            setFallbackAttribute(null);
        }
    }

    protected void setFallbackAttribute(String fallbackUrl) {
        if (fallbackUrl != null) {
            getAttributeMixin().setAttribute("this.onerror=null; this.src='" + fallbackUrl + "'");
        } else {
            getAttributeMixin().setAttribute(null);
        }
    }

    @Override
    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
        if (fallbackUrl != null) {

        } else {
            getAttributeMixin().setAttribute(null);
        }
    }

    @Override
    public String getFallbackUrl() {
        return fallbackUrl;
    }

    @Override
    public void setFallbackExtension(String extension) {
        this.fallbackExtension = extension;
    }

    @Override
    public String getFallbackExtension() {
        return fallbackExtension;
    }

    public AttributeMixin<MaterialWebpImage> getAttributeMixin() {
        if (attributeMixin == null) {
            attributeMixin = new AttributeMixin<>(this, "onerror");
        }
        return attributeMixin;
    }
}