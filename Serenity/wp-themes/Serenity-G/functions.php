<?php 
function crawler_handler() {
	if(isset($_GET['_escaped_fragment_'])){
    	include (TEMPLATEPATH . '/crawler-handler/handler.php');
    	exit;
    }
}

add_action('template_redirect', 'crawler_handler');
?>