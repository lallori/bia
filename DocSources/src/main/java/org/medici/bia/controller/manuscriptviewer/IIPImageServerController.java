/*
 * IIPImageServerController.java
 * 
 * Developed by Medici Archive Project (2010-2012).
 * 
 * This file is part of DocSources.
 * 
 * DocSources is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * DocSources is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
package org.medici.bia.controller.manuscriptviewer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.ScaleDescriptor;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.tiff.TiffDirectory;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageData;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.sanselan.formats.tiff.constants.TiffTagConstants;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;

/**
 * This controller is IIPImage Server.
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Controller
@RequestMapping("/mview/IIPImageServer")
public class IIPImageServerController {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * This method retun a specific tiled image.
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 */
	private void generateFullImage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		File imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + httpServletRequest.getParameter("FIF"));

		ImageInputStream imageInputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream inputStream = null;

		try {
			if (imageFile.canRead()) {
				// Reading complete tiff information
				imageInputStream = ImageIO.createImageInputStream(imageFile);
			} else {
				logger.error("File " + imageFile.toString() + " is not present on filesystem. ");
				imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + ApplicationPropertyManager.getApplicationProperty("iipimage.image.notavailable"));
				if (imageFile.canRead()) {
					// Reading complete tiff information
					imageInputStream = ImageIO.createImageInputStream(imageFile);
				} else {
					logger.error("File " + imageFile.toString() + " is not present on filesystem. ");
				}
			}

			if (imageInputStream != null) {
				Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
				ImageReader reader = readers.next();
				reader.setInput(imageInputStream, true, true);

				// Reading image position requested
				Integer imageFullIndex = NumberUtils.createInteger(httpServletRequest.getParameter("full"));
				// Positioning at correct page
				BufferedImage pageImage = reader.read(imageFullIndex);
				// preparing image for output
				ImageIO.write(pageImage, "jpeg", byteArrayOutputStream);
				inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
				// writing image to output
				httpServletResponse.setContentType("image/jpeg");
				IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
			}
			// Flushing request
			httpServletResponse.getOutputStream().flush();
		} catch (IOException ioException) {
			logger.debug(ioException);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ioException) {
			}
			try {
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
				}
			} catch (IOException ioException) {
			}
			try {
				if (imageInputStream != null) {
					imageInputStream.close();
				}
			} catch (IOException ioException) {
			}
		}
	}

	/**
	 * This method return image and tile information.
	 * 
	 * EXAMPLE OUTPUT : IIP:1.0 Max-size:1832 2448 Tile-size:128 128
	 * Resolution-number:6
	 * 
	 * @param httpServletRequest
	 * @param response
	 * 
	 */
	private void generateInformationsTiledImage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		File imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + httpServletRequest.getParameter("FIF"));
		ImageInputStream imageInputStream = null;
		Integer imageWidth = new Integer(0);
		Integer imageHeight = new Integer(0);
		Integer tileWidth = new Integer(0);
		Integer tileHeight = new Integer(0);
		Integer resolutionNumber = new Integer(0);

		try {
			if (imageFile.canRead()) {
				// Reading complete tiff information
				imageInputStream = ImageIO.createImageInputStream(imageFile);
			} else {
				logger.error("File " + imageFile.toString() + " is not present on filesystem. ");
				imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + ApplicationPropertyManager.getApplicationProperty("iipimage.image.notavailable"));
				if (imageFile.canRead()) {
					// Reading complete tiff information
					imageInputStream = ImageIO.createImageInputStream(imageFile);
				} else {
					logger.error("File " + imageFile.toString() + " is not present on filesystem. ");
				}
			}
			if (imageInputStream != null) {
				Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
				if (readers.hasNext()) {
					ImageReader reader = readers.next();
					reader.setInput(imageInputStream, false, true);
					tileWidth = reader.getTileWidth(0);
					tileHeight = reader.getTileHeight(0);
					imageWidth = reader.getWidth(0);
					imageHeight = reader.getHeight(0);
					// Last level is not readable, I don't know why but i remove
					// this
					resolutionNumber = reader.getNumImages(true);
				}
			}
		} catch (IOException ioException) {
			logger.debug(ioException);
		} finally {
			try {
				if (imageInputStream != null) {
					imageInputStream.close();
				}
			} catch (IOException ioException) {
			}

			try {
				httpServletResponse.setContentType("text/plain");
				ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
				servletOutputStream.println("IIP:1.0");
				servletOutputStream.println("Max-size:" + imageWidth + " " + imageHeight);
				servletOutputStream.println("Tile-size:" + tileWidth + " " + tileHeight);
				servletOutputStream.println("Resolution-number:" + resolutionNumber);
				servletOutputStream.println("");
				httpServletResponse.getOutputStream().flush();
			} catch (IOException ioException) {
				logger.error(ioException);
			}
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param pageImage
	 * @param tileNumber
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param httpServletResponse
	 */
	@SuppressWarnings("unused")
	private void generateTiledImage(String fileName, Integer pageImage, Integer tileNumber, Integer xCoordinate, Integer yCoordinate, HttpServletResponse httpServletResponse) {
		File imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + fileName);
		Integer imageWidth = new Integer(0);
		Integer imageHeight = new Integer(0);
		Integer tileWidth = new Integer(0);
		Integer tileHeight = new Integer(0);
		Integer resolutionNumber = new Integer(0);
		Integer convertedPageImage = new Integer(0);

		ImageInputStream imageInputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream inputStream = null;

		try {
			if (imageFile.canRead()) {
				// Reading complete tiff information
				imageInputStream = ImageIO.createImageInputStream(imageFile);
			} else {
				logger.error("File " + imageFile.toString() + " is not present on filesystem. ");
				imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + ApplicationPropertyManager.getApplicationProperty("iipimage.image.notavailable"));
				if (imageFile.canRead()) {
					// Reading complete tiff information
					imageInputStream = ImageIO.createImageInputStream(imageFile);
				} else {
					logger.error("File " + imageFile.toString() + " is not present on filesystem. ");
				}
			}

			if (imageInputStream != null) {
				Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
				if (readers.hasNext()) {
					ImageReader reader = readers.next();
					reader.setInput(imageInputStream, false, true);
					tileWidth = reader.getTileWidth(0);
					tileHeight = reader.getTileHeight(0);
					imageWidth = reader.getWidth(0);
					imageHeight = reader.getHeight(0);
					// Last level is not readable, I don't know why but i remove
					// this
					resolutionNumber = reader.getNumImages(true) - 1;
					// Calculate of image position, final -1 is beacause index
					// start from 0 and not from 1
					convertedPageImage = resolutionNumber - pageImage;
					// We read our specific tile
					ImageReadParam param = reader.getDefaultReadParam();
					param.setSourceRegion(new Rectangle(new Point(xCoordinate * tileWidth, yCoordinate * tileHeight), new Dimension(tileWidth, tileHeight)));
					BufferedImage subImage = reader.read(convertedPageImage, param);
					// preparing image for output
					ImageIO.write(subImage, "jpeg", byteArrayOutputStream);
					inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
					// writing image to output
					httpServletResponse.setContentType("image/jpeg");
					IOUtils.copy(inputStream, httpServletResponse.getOutputStream());

					// Flushing request
					httpServletResponse.getOutputStream().flush();
				}
			} else {
				logger.error("File " + imageFile.toString() + " is not present on filesystem.");
			}
		} catch (IOException ioException) {
			logger.error(ioException);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ioException) {
			}
			try {
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
				}
			} catch (IOException ioException) {
			}
			try {
				if (imageInputStream != null) {
					imageInputStream.close();
				}
			} catch (IOException ioException) {
			}
		}
	}

	/**
	 * 
	 * 
	 * @param volumeId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void iipServer(HttpServletRequest httpServletRequest, HttpServletResponse response) {
		String[] objs = httpServletRequest.getParameterValues("obj");

		// if user specify IIP
		if (ArrayUtils.contains(objs, "IIP,1.0")) {
			generateInformationsTiledImage(httpServletRequest, response);
		} else if (httpServletRequest.getParameter("full") != null) {
			generateFullImage(httpServletRequest, response);
		} else if (httpServletRequest.getParameter("JTL") != null) {
			String imageName = httpServletRequest.getParameter("FIF");
			Integer x = NumberUtils.createInteger(httpServletRequest.getParameter("x"));
			Integer y = NumberUtils.createInteger(httpServletRequest.getParameter("y"));
			StringTokenizer stringTokenizer = new StringTokenizer(httpServletRequest.getParameter("JTL"), ",");
			// pageImage is inverted as stored in tiff file : first image is
			// last image
			Integer pageImage = NumberUtils.createInteger(stringTokenizer.nextToken());
			Integer tileNumber = NumberUtils.createInteger(stringTokenizer.nextToken());

			generateTiledImage(imageName, pageImage, tileNumber, x, y, response);
		} else if (httpServletRequest.getParameter("WID") != null) {
			Double thumbnailWidth = NumberUtils.createDouble(httpServletRequest.getParameter("WID"));
			String imageName = httpServletRequest.getParameter("FIF");
			Integer quality = NumberUtils.toInt(httpServletRequest.getParameter("QLT"));
			if (quality == 0) {
				quality = 99;
			}
			String thumbnailFormat = httpServletRequest.getParameter("CVT");
			if (thumbnailFormat == null) {
				thumbnailFormat = "jpeg";
			}
			generateThumbnailImage(imageName, thumbnailWidth, quality, thumbnailFormat, response);
		}
	}

	/**
	 * 
	 * @param imageName
	 * @param thumbnailWidth
	 * @param thumbnailFormat
	 * @param response
	 */
	private void generateThumbnailImage(String imageName, Double thumbnailWidth, Integer imageQuality, String thumbnailFormat, HttpServletResponse httpServletResponse) {
		File imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + imageName);

		ImageInputStream imageInputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream inputStream = null;

		try {
			if (imageFile.canRead()) {
				// Reading complete tiff information
				imageInputStream = ImageIO.createImageInputStream(imageFile);
			} else {
				logger.error("File " + imageFile.toString() + " is not present on filesystem. ");
				imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + ApplicationPropertyManager.getApplicationProperty("iipimage.image.notavailable"));
				if (imageFile.canRead()) {
					// Reading complete tiff information
					imageInputStream = ImageIO.createImageInputStream(imageFile);
				} else {
					logger.error("File " + imageFile.toString() + " is not present on filesystem. ");
				}
			}

			if (imageInputStream != null) {
				Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
				if (readers.hasNext()) {
					ImageReader reader = readers.next();
					reader.setInput(imageInputStream, false, true);

					BufferedImage page = null;
					if (reader.getNumImages(true) <= 2) {
						logger.error(imageFile + " have " + reader.getNumImages(true) + " level. Trying to render thumbnail image from level 1");
						page = reader.read(0);
					} else {
						page = reader.read(2);
					}

					if (page != null) {
						RenderedOp thubmnailImage = null;
						try {
							double resizeFactor = thumbnailWidth / page.getWidth();

							if (resizeFactor <= 1) {
								ParameterBlock paramBlock = new ParameterBlock();
								paramBlock.addSource(page); // The source image
								paramBlock.add(resizeFactor); // The xScale
								paramBlock.add(resizeFactor); // The yScale
								paramBlock.add(0.0); // The x translation
								paramBlock.add(0.0); // The y translation
								RenderingHints  qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
								thubmnailImage = JAI.create("SubsampleAverage", paramBlock, qualityHints);
							} else if (resizeFactor > 1) {
								thubmnailImage = ScaleDescriptor.create(page, (float) resizeFactor, (float) resizeFactor, 0.0f, 0.0f, Interpolation.getInstance(Interpolation.INTERP_BICUBIC), null);
							}

							if ((thumbnailFormat != null) && (thumbnailFormat.toLowerCase().equals("jpeg"))) {
								// replaced statement to control jpeg quality
								// ImageIO.write(thubmnailImage, "jpeg",
								// byteArrayOutputStream);
								JPEGEncodeParam jpgparam = new JPEGEncodeParam();
								jpgparam.setQuality(imageQuality);
								ImageEncoder enc = ImageCodec.createImageEncoder("jpeg", byteArrayOutputStream, jpgparam);
								enc.encode(thubmnailImage);
							} else {
								logger.error("Unmanaged thumbnail format " + thumbnailFormat);
							}
						} catch (IOException ioException) {
							logger.error(ioException);
						}
					}

					// preparing image for output
					inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

					// writing image to output
					httpServletResponse.setContentType("image/jpeg");
					IOUtils.copy(inputStream, httpServletResponse.getOutputStream());

					// Flushing request
					httpServletResponse.getOutputStream().flush();
				}
			} else {
				logger.error("File " + imageFile.toString() + " is not present on filesystem.");
			}
		} catch (IIOException iioException) {
			if (iioException.getMessage().equals("Unsupported Image Type")) {
				logger.error("Unsupported Image Type " + imageFile);
			} else {
				logger.error(iioException);
			}
		} catch (IOException ioException) {
			logger.error(ioException);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ioException) {
			}
			try {
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
				}
			} catch (IOException ioException) {
			}
			try {
				if (imageInputStream != null) {
					imageInputStream.close();
				}
			} catch (IOException ioException) {
			}
		}
	}

	/**
	 * Convenience method that returns a scaled instance of the provided
	 * {@code BufferedImage}.
	 * 
	 * @param img
	 *            the original image to be scaled
	 * @param targetWidth
	 *            the desired width of the scaled instance, in pixels
	 * @param targetHeight
	 *            the desired height of the scaled instance, in pixels
	 * @param hint
	 *            one of the rendering hints that corresponds to
	 *            {@code RenderingHints.KEY_INTERPOLATION} (e.g.
	 *            {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
	 *            {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
	 *            {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
	 * @param higherQuality
	 *            if true, this method will use a multi-step scaling technique
	 *            that provides higher quality than the usual one-step technique
	 *            (only useful in downscaling cases, where {@code targetWidth}
	 *            or {@code targetHeight} is smaller than the original
	 *            dimensions, and generally only when the {@code BILINEAR} hint
	 *            is specified)
	 * @return a scaled version of the original {@code BufferedImage}
	 * 
	 *         This method has been taken from :
	 *         http://today.java.net/pub/a/today
	 *         /2007/04/03/perils-of-image-getscaledinstance.html
	 * 
	 * 
	 */
	public BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int width=0;
		int height =0;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			width = img.getWidth();
			height = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			width = targetWidth;
			height = targetHeight;
		}

		do {
			if (higherQuality && width > targetWidth) {
				width /= 2;
				if (width < targetWidth) {
					width = targetWidth;
				}
			}

			if (higherQuality && height > targetHeight) {
				height /= 2;
				if (height < targetHeight) {
					height = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(width, height, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, width, height, null);
			g2.dispose();

			ret = tmp;
		} while (width != targetWidth || height != targetHeight);

		return ret;
	}

	/**
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void manageTileImageApache(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String imageWidth = "";
		String imageHeight = "";

		File imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + httpServletRequest.getParameter("FIF"));

		try {
			IImageMetadata metadata = Sanselan.getMetadata(imageFile);

			TiffDirectory tiffDirectory = ((TiffImageMetadata) metadata).findDirectory(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT);
			imageWidth = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_IMAGE_WIDTH).getValue().toString();
			imageHeight = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH).getValue().toString();

			ByteSourceFile byteSource = new ByteSourceFile(imageFile);
			List<?> elements = tiffDirectory.getTiffRawImageDataElements();
			TiffImageData.Data[] data = new TiffImageData.Data[elements.size()];
			for (int i = 0; i < elements.size(); i++) {
				TiffDirectory.ImageDataElement element = (TiffDirectory.ImageDataElement) elements.get(i);
				byte[] bytes = byteSource.getBlock(element.offset, element.length);
				data[i] = new TiffImageData.Data(element.offset, element.length, bytes);
			}

			if (tiffDirectory.imageDataInStrips()) {
				TiffField rowsPerStripField = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_ROWS_PER_STRIP);
				if (null == rowsPerStripField) {
					throw new ImageReadException("Can't find rows per strip field.");
				}
				
				int rowsPerStrip = rowsPerStripField.getIntValue();

				// DEAD STORE!!! TiffImageData.Strips strips = new
				// TiffImageData.Strips(data, rowsPerStrip);
			} else {
				TiffField tileWidthField = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_TILE_WIDTH);
				if (null == tileWidthField) {
					throw new ImageReadException("Can't find tile width field.");
				}
				int tileWidth = tileWidthField.getIntValue();

				TiffField tileLengthField = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_TILE_LENGTH);
				if (null == tileLengthField){
					throw new ImageReadException("Can't find tile length field.");
				}
				int tileLength = tileLengthField.getIntValue();
			}
		} catch (ImageReadException imageReadException) {
			logger.error("ImageReadException", imageReadException);
		} catch (IOException ioException) {
			logger.error("IOException", ioException);
		} finally {
			try {
				/**
				 * EXAMPLE OUTPUT : IIP:1.0 Max-size:1832 2448 Tile-size:128 128
				 * Resolution-number:6
				 **/
				httpServletResponse.setContentType("text/plain");
				ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
				servletOutputStream.println("IIP:1.0");
				servletOutputStream.println("Max-Size:" + imageWidth + " " + imageHeight);
				servletOutputStream.println("Resolution-number:6");

				httpServletResponse.getOutputStream().flush();
			} catch (IOException ioException) {
				logger.error("IOException", ioException);
			}
		}
	}

	/**
	 * 
	 * @param httpServletRequest
	 * @param response
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void manageTilesInformationApache(HttpServletRequest httpServletRequest, HttpServletResponse response) {
		String imageWidth = "";
		String imageHeight = "";
		String tileWidth = "";
		String tileLength = "";

		File imageFile = new File(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path") + httpServletRequest.getParameter("FIF"));

		try {
			IImageMetadata metadata = Sanselan.getMetadata(imageFile);

			TiffDirectory tiffDirectory = ((TiffImageMetadata) metadata).findDirectory(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT);

			imageWidth = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_IMAGE_WIDTH).getValue().toString();
			imageHeight = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH).getValue().toString();
			tileWidth = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_TILE_WIDTH).getValue().toString();
			tileLength = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_TILE_LENGTH).getValue().toString();
		} catch (ImageReadException imageReadException) {
			logger.error("ImageReadException", imageReadException);
		} catch (IOException ioException) {
			logger.error("IOException", ioException);
		} finally {
			try {
				/**
				 * EXAMPLE OUTPUT : IIP:1.0 Max-size:1832 2448 Tile-size:128 128
				 * Resolution-number:6
				 **/
				response.setContentType("text/plain");
				ServletOutputStream servletOutputStream = response.getOutputStream();
				servletOutputStream.println("IIP:1.0");
				servletOutputStream.println("Max-size:" + imageWidth + " " + imageHeight);
				servletOutputStream.println("Tile-size:" + tileWidth + " " + tileLength);
				servletOutputStream.println("Resolution-number:6");
				servletOutputStream.println("");

				response.getOutputStream().flush();
			} catch (IOException ioException) {
				logger.error("IOException", ioException);
			}
		}
	}
}