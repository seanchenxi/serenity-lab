<?php
    // find the which is requesting
	$token = strtolower($_GET['_escaped_fragment_']);
	$place = "home";
    $slug = "";
    $page = 0;
	if (strpos($token, '/') !== false) {
		$token = substr($token, strpos($token, '/') + 1);
    	$tokens = explode("/", $token);
    	$count = 0;
    	foreach ($tokens as $val) {
    		if(strpos($val, 'page') !== false){
    			$page = intval(substr($val, 4));
    		}else if($count == 0){
    			$place = $val;
    		}else if($count == 1){
    			$slug = $val;
    		}
    		$count++;
    	}
    }
    
    $base_url = get_bloginfo('wpurl').'/#!';
    $handled_title = "";
    $page_url = $base_url.'/'.$place;
    if(!empty($slug)){
        $page_url = $page_url.'/'.$slug;
        if($page > 0){
            $page_url = $page_url.'/page'.$page;
        }
    }
    // create query arguments
    $args = array('post_status' => 'publish', 'post_type' => array('post', 'page'));
    $is_single = ( ("article" == $place) || ("about" == $place) );
    if( $is_single ){
        // get post by slug
        $args = array_merge(array('name' => $slug), $args);
        $handled_title = $slug;
    }else if( "home" == $place ){
        // get recent posts
    }else if( "category" == $place ){
        // get category posts
        $args = array_merge(array('category_name' => $slug), $args);
        $handled_title = "Category:&nbsp;".$slug;
    }else if( "tag" == $place ){
        // get tag posts
        $args = array_merge(array('tag' => $slug), $args);
        $handled_title = "Tag:&nbsp;".$slug;
    }else if( "search" == $place){
        // get search posts
    }
    if(!$is_single){
        $args = array_merge(array('posts_per_page' => 10, 'paged' => $page), $args);
    }

    global $post;
    $the_query = new WP_Query( $args );
    if($is_single){
        include (TEMPLATEPATH . '/crawler-handler/handler-single.php');
    }else{
        include (TEMPLATEPATH . '/crawler-handler/handler-multi.php');
    }
?>

