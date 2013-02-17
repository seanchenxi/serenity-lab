/*******************************************************************************
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.seanchenxi.gwt.serenity.client.resource.image;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImgSources extends ClientBundle {
	
	@Source("white/home.png")
	ImageResource icon_Home_white();
	
	@Source("white/comment.png")
	ImageResource icon_Comment_white();
	
	@Source("white/email.png")
	ImageResource icon_Email_white();
	
	@Source("white/tag.png")
	ImageResource icon_Tag_white();
	
	@Source("white/grid.png")
	ImageResource icon_Grid_white();
	
	@Source("white/info.png")
	ImageResource icon_Info_white();
	
	@Source("black/home.png")
	ImageResource icon_Home_black();
	
	@Source("black/comment.png")
	ImageResource icon_Comment_black();
	
	@Source("black/email.png")
	ImageResource icon_Email_black();
	
	@Source("black/tag.png")
	ImageResource icon_Tag_black();
	
	@Source("black/grid.png")
	ImageResource icon_Grid_black();
	
	@Source("black/info.png")
	ImageResource icon_Info_black();
	
	@Source("black/newer.png")
	ImageResource icon_Newer_black();
	
	@Source("black/older.png")
	ImageResource icon_Older_black();
	
	@Source("black/search.png")
	ImageResource icon_Search_black();
	
	@Source("avatar32.png")
    ImageResource avatar32();
}
