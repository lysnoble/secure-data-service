<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<#attempt>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${CONTEXT_ROOT_PATH}/static/js/3p/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${CONTEXT_ROOT_PATH}/static/js/3p/jquery-ui/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="${CONTEXT_ROOT_PATH}/static/js/3p/jquery-ui/js/bootstrap-dropdown.js"></script>
<script type="text/javascript" src="${CONTEXT_ROOT_PATH}/static/js/3p/jqGrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${CONTEXT_ROOT_PATH}/static/js/3p/raphael-min.js"></script>
<script type="text/javascript" src="${CONTEXT_ROOT_PATH}/static/js/dashboardUtil.js"></script>
<script type="text/javascript" src="${CONTEXT_ROOT_PATH}/static/js/fuelGaugeWidget.js"></script>
<link rel="stylesheet" type="text/css" href="${CONTEXT_ROOT_PATH}/static/js/3p/jquery-ui/css/custom/jquery-ui-1.8.18.custom.css" media="screen" />
<link rel="stylesheet" type="text/css" href="${CONTEXT_ROOT_PATH}/static/js/3p/jqGrid/css/ui.jqgrid.css" media="screen" />
<link rel="stylesheet" type="text/css" href="${CONTEXT_ROOT_PATH}/static/css/common.css" media="screen" />

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', ${googleAnalyticsTrackerId}]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>
<body>
<#include "layout/layout_header.ftl">
<#include "${page_to_include}">


<#include "layout/layout_footer.ftl">
</body>
</html>
<#recover>
${logger.error(.error)}
<#include "layout/layout_header.ftl">
<#include "error.ftl">
<#include "layout/layout_footer.ftl">
</#attempt>